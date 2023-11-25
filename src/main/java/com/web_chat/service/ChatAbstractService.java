package com.web_chat.service;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.web_chat.model.FILE;
import com.web_chat.model.Message;
import com.web_chat.websocket.WebSocket;

public abstract class ChatAbstractService {
	protected static final Set<WebSocket> webSockets = new CopyOnWriteArraySet<WebSocket>();
	public abstract boolean register(WebSocket websocket);
	public abstract boolean close(WebSocket websocket);
	public abstract void sendMessageToOne(Message msg, Queue<FILE> files);
	public abstract void sendMessageToConversation(Message msg, Queue<FILE> files);
}
