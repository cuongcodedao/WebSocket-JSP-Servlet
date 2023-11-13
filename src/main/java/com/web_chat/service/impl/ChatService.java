package com.web_chat.service.impl;

import java.io.IOException;

import javax.websocket.EncodeException;

import com.web_chat.model.Message;
import com.web_chat.service.ChatAbstractService;
import com.web_chat.websocket.WebSocket;

public class ChatService extends ChatAbstractService{
	private static ChatService instance = null;
	
	public static synchronized ChatService getInstance() {
        if (instance == null) {
            instance = new ChatService();
        }
        return instance;
    }
	
	@Override
	public boolean register(WebSocket websocket) {
		return webSockets.add(websocket);
	}
	
	@Override
	public boolean close(WebSocket websocket) {
		return webSockets.remove(websocket);
	}
	
	@Override
	public void sendMessageToOne(Message msg) {
		for(WebSocket webSocket:webSockets) {
			if(webSocket.getUsername().equals(msg.getTo())) {
				try {
					webSocket.getSession().getBasicRemote().sendObject(msg);
				} catch (IOException | EncodeException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
