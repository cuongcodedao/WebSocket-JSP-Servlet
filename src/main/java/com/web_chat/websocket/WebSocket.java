package com.web_chat.websocket;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.web_chat.model.FILE;
import com.web_chat.model.Message;
import com.web_chat.service.ChatAbstractService;
import com.web_chat.service.IMessageService;
import com.web_chat.service.IUserService;
import com.web_chat.service.impl.ChatService;
import com.web_chat.service.impl.MessageService;
import com.web_chat.service.impl.UserService;

@ServerEndpoint(value = "/chat_box/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class WebSocket {
	private Session session;
	private String username;
	private Queue<FILE> files = new LinkedList<>();

	private IMessageService messageService = MessageService.getInstance();
	private IUserService userService = UserService.getInstance();
	private ChatAbstractService chatAbstractService = ChatService.getInstance();
	
	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {
		if(chatAbstractService.register(this)) {
			this.session = session;
			this.username = username;
			userService.setOnline(username, 1);
		}
		
	}

	@OnMessage
	public void onMessage(Session session, Message message) throws IOException, EncodeException {
		LocalDateTime localDateTime = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		message.setDate_sent(timestamp);
		System.out.println(message.getType());
		messageService.save(message);
		if(message.getTo() != null) {
			chatAbstractService.sendMessageToOne(message, files);
		}
		else {
			chatAbstractService.sendMessageToConversation(message, files);
		}
	}
	@OnMessage
	public void processUpload(Session session, byte[] byteArray, boolean last) {

//		String rootPath = "C:\\Users\\DANG VAN CUONG\\eclipse-workspace\\web_chat\\archive";
//		ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
//		BufferedImage bImage2;
//		try {
//		    bImage2 = ImageIO.read(bis);
//		    String pathImage = rootPath+"\\test1.jpg";
//		    ImageIO.write(bImage2, "jpg", new File(pathImage));
//		} catch (IOException e) {
//		    e.printStackTrace();
//		}
	}

	@OnClose
	public void onClose(Session session) throws IOException, EncodeException {
		if(chatAbstractService.close(this)) {
			userService.setOnline(username, 0);
		}
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		throwable.printStackTrace();
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
