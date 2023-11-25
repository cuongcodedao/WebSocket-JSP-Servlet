package com.web_chat.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.websocket.EncodeException;

import com.web_chat.model.FILE;
import com.web_chat.model.Message;
import com.web_chat.model.User;
import com.web_chat.service.ChatAbstractService;
import com.web_chat.service.IUserService;
import com.web_chat.websocket.WebSocket;

public class ChatService extends ChatAbstractService{
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
	
	@Override
	public void sendMessageToOne(Message msg, Queue<FILE> files) {
		if(!msg.getType().equals("text")) {
			String rootPath = "C:\\Users\\DANG VAN CUONG\\eclipse-workspace\\web_chat\\archive";
			String pathDesFile = rootPath + "\\" + msg.getFrom() + "_" + msg.getContent();
			FILE fileModel = new FILE();
			fileModel.setReceiver(msg.getTo());
			fileModel.setSender(msg.getFrom());
			File desFile = new File(pathDesFile);
			try {
				fileModel.setFileOutputStream(new FileOutputStream(desFile, false));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			files.add(fileModel);
		}
		else {
			webSockets.stream().filter(websocket -> websocket.getUsername().equals(msg.getTo()))
			.forEach(websocket -> {
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

	@Override
	public void sendMessageToConversation(Message msg, Queue<FILE> files) {
		List<User> usersInGroup = userService.findAllByConversationId(msg.getConversation_id());
		Set<String> usernamesInGroup = usersInGroup.stream().map(User::getUsername).collect(Collectors.toSet());
		
		webSockets.stream().filter(websocket -> usernamesInGroup.contains(websocket.getUsername())
				&& !websocket.getUsername().equals(msg.getFrom()))
					.forEach(websocket -> {
					try {
						websocket.getSession().getBasicRemote().sendObject(msg);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (EncodeException e) {
						e.printStackTrace();
					}
				});
		
	}
	public void processingFile(Queue<FILE> files, boolean last, byte[] byteArray) {
		if(!last) {
			
		}
		String rootPath = "C:\\Users\\DANG VAN CUONG\\eclipse-workspace\\web_chat\\archive";
		ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
		BufferedImage bImage2;
		try {
		    bImage2 = ImageIO.read(bis);
		    String pathImage = rootPath+"\\test1.jpg";
		    ImageIO.write(bImage2, "jpg", new File(pathImage));
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
}
