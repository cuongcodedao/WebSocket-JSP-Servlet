package com.web_chat.service.impl;

import java.util.List;

import javax.inject.Inject;

import com.web_chat.dao.IMessageDAO;
import com.web_chat.dao.impl.MessageDAO;
import com.web_chat.model.Message;
import com.web_chat.service.IMessageService;

public class MessageService implements IMessageService{
	
	private static MessageService instance = null;
	
	private IMessageDAO messageDAO = MessageDAO.getInstance();

    public static synchronized MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }
	

	@Override
	public List<Message> findAll() {
		return messageDAO.findAll();
	}


	@Override
	public Message save(Message message) {
		int id = messageDAO.save(message);
		return messageDAO.findById(id);
	}

	@Override
	public List<Message> findAllMessageBySenderAndReceiver(String sender, String receiver) {
		return messageDAO.getAllMessageBySenderandReceiver(sender, receiver);
	}
	
	
}
