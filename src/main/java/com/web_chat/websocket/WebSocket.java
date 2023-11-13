package com.web_chat.websocket;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

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
	
	private static Map<Session, String> users = new ConcurrentHashMap<>();

	private IMessageService messageService = MessageService.getInstance();
	private IUserService userService = UserService.getInstance();
	private ChatAbstractService chatAbstractService = ChatService.getInstance();
	
	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {
		if(chatAbstractService.register(this)) {
			this.session = session;
			this.username = username;
			userService.setOnline(username, 1);
			users.put(this.session, username);
		}
		
	}

	@OnMessage
	public void onMessage(Session session, Message message) throws IOException, EncodeException {
		LocalDateTime localDateTime = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		message.setDate_sent(timestamp);
		messageService.save(message);
		chatAbstractService.sendMessageToOne(message);
	}

	@OnClose
	public void onClose(Session session) throws IOException, EncodeException {
		if(chatAbstractService.close(this)) {
			userService.setOnline(username, 0);
		}
		users.remove(session);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		throwable.printStackTrace();
	}

	private void broadcast(Message message) {
		for (Session userSession : users.keySet()) {
			synchronized (userSession) {
				try {
					if (!userSession.getId().equals(this.session.getId()))
						userSession.getBasicRemote().sendObject(message);
				} catch (IOException | EncodeException e) {
					e.printStackTrace();
				}
			}
		}
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
