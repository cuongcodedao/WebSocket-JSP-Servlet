package com.web_chat.service;

import java.util.List;

import com.web_chat.model.Message;

public interface IMessageService {
	public List<Message> findAll();
	public Message save(Message message);
	public List<Message> findAllMessageBySenderAndReceiver(String sender, String receiver);
}
