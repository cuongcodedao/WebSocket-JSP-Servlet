package com.web_chat.service;

import java.util.List;

import com.web_chat.model.Message;

public interface IMessageService {
	public List<Message> findAll();
	public Message save(Message message);
	public void deleteMessageAddFriend(String user1, String user2);
	public List<Message> findAllMessageBySenderAndReceiver(String sender, String receiver);
	public List<Message> findAllMessageByConversationName(String name);
}
