package com.web_chat.dao;

import java.util.List;

import com.web_chat.model.Message;

public interface IMessageDAO {
	public List<Message> findAll();
	public List<Message> findByUsername(String username);
	public int save(Message message);
	public Message findById(int id);
	public void deleteAddFriendMessage(String user1, String user2);
	public List<Message> getAllMessageBySenderandReceiver(String sender, String receiver);
	public List<Message> getAllMesssageByConversationId(int id);
}
