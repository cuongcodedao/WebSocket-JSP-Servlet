package com.web_chat.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import javax.websocket.EncodeException;

import com.web_chat.controller.FileController;
import com.web_chat.model.FILE;
import com.web_chat.model.Message;
import com.web_chat.model.User;
import com.web_chat.service.ChatAbstractService;
import com.web_chat.service.IUserService;
import com.web_chat.websocket.WebSocket;

public class ChatService extends ChatAbstractService {
	private static ChatService instance = null;

	public static synchronized ChatService getInstance() {
		if (instance == null) {
			instance = new ChatService();
		}
		return instance;
	}

	private IUserService userService = UserService.getInstance();

	@Override
	public boolean register(WebSocket websocket) {
		return webSockets.add(websocket);
	}

	@Override
	public boolean close(WebSocket websocket) {
		return webSockets.remove(websocket);
	}

	private void addIntoQueue(Message msg, Queue<FILE> files) {
//		String rootPath = "C:\\Users\\DANG VAN CUONG\\eclipse-workspace\\web_chat\\archive";
		String rootPath = FileController.rootPath;
		String receiver = msg.getTo();
		if(receiver == null) {
			receiver = msg.getConversation_id() + "";
		}
		File theDir = new File(rootPath + "/" + msg.getFrom() + "_" +receiver);
		if (!theDir.exists()){
		    theDir.mkdirs(); 
		}
		String pathDesFile = theDir.getPath() + "/" + msg.getContent();
		System.out.println(pathDesFile);
		FILE fileModel = new FILE();
		fileModel.setNameFile(msg.getContent());
		fileModel.setReceiver(msg.getTo());
		fileModel.setConversation_id(msg.getConversation_id());
		fileModel.setSender(msg.getFrom());
		fileModel.setUrl(pathDesFile);
		fileModel.setType(msg.getType());
		File desFile = new File(pathDesFile);
		try {
			fileModel.setFileOutputStream(new FileOutputStream(desFile, false));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		files.add(fileModel);
	}

	@Override
	public void sendMessageToOne(Message msg, Queue<FILE> files) {
		if (msg.getType().contains("/")) {
			addIntoQueue(msg, files);
		} else {
			webSockets.stream().filter(websocket -> websocket.getUsername().equals(msg.getTo())).forEach(websocket -> {
				try{
					websocket.getSession().getBasicRemote().sendObject(msg);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (EncodeException e) {
					e.printStackTrace();
				}
			});
		}
	}

	@Override
	public void sendMessageToConversation(Message msg, Queue<FILE> files) {
		if (msg.getType().contains("/")) {
			addIntoQueue(msg, files);
		} else {
			List<User> usersInGroup = userService.findAllByConversationId(msg.getConversation_id());
			Set<String> usernamesInGroup = usersInGroup.stream().map(User::getUsername).collect(Collectors.toSet());
			
			webSockets.stream().filter(websocket -> usernamesInGroup.contains(websocket.getUsername())
					&& !websocket.getUsername().equals(msg.getFrom())).forEach(websocket -> {
						try {
							websocket.getSession().getBasicRemote().sendObject(msg);
						} catch (IOException e) {
							e.printStackTrace();
						} catch (EncodeException e) {
							e.printStackTrace();
						}
					});
		}
		
	}

	public void handlerFile(Queue<FILE> files, boolean last, ByteBuffer buffer) {
		try {
			if (!last) {
				while (buffer.hasRemaining()) {
					files.peek().getFileOutputStream().write(buffer.get());
				}
			} else {
				while (buffer.hasRemaining()) {
					files.peek().getFileOutputStream().write(buffer.get());
				}
				files.peek().getFileOutputStream().flush();
				files.peek().getFileOutputStream().close();
				Message message = new Message();
				String content = files.peek().getNameFile();
				String type = files.peek().getType();
				if(type.startsWith("image")) {
					message.setType("image");
				}
				else if(type.startsWith("video")) {
					message.setType("video");
				}
				else if(type.contains("audio")) {
					message.setType("audio");
				}
				else if(type.endsWith("pdf")) {
					message.setType("pdf");
				}
				message.setConversation_id(files.peek().getConversation_id());
				message.setFrom(files.peek().getSender());
				message.setTo(files.peek().getReceiver());
				message.setContent(content);
				if (message.getTo() == null) {
					sendMessageToConversation(message, files);
				} else {
					sendMessageToOne(message, files);
				}
				files.remove();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

}
