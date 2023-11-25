package com.web_chat.service.impl;

import java.util.List;

import com.web_chat.dao.IConversationDAO;
import com.web_chat.dao.IUserDAO;
import com.web_chat.dao.impl.ConversationDAO;
import com.web_chat.dao.impl.UserDAO;
import com.web_chat.model.Conversation;
import com.web_chat.model.User;
import com.web_chat.service.IConversationService;

public class ConversationService implements IConversationService{
	
	private static ConversationService instance = null;
	private ConversationService() {
		
	}
	public static synchronized ConversationService getInstance() {
		if(instance == null) {
			instance = new ConversationService();
		}
		return instance;
	}
	private IConversationDAO conversationDAO = ConversationDAO.getInstance();
	private IUserDAO userDAO = UserDAO.getInstance();

	@Override
	public Conversation createNew(String usernameCreater, String group_name) {
		Conversation cvs = conversationDAO.createNew(usernameCreater, group_name);
		User creater = userDAO.findByUserName(usernameCreater);
		conversationDAO.addUser(creater.getId(), cvs.getId());
		return cvs;
	}

	@Override
	public Conversation findById(int id) {
		return conversationDAO.findById(id);
	}
	@Override
	public List<Conversation> findAllByUsername(String username) {
		return conversationDAO.findAllByUsername(username);
	}
	@Override
	public Conversation findByName(String name) {
		return conversationDAO.findByName(name);
	}
	@Override
	public int addUser(String username, String conversationName) {
		User us = userDAO.findByUserName(username);
		Conversation cvs = conversationDAO.findByName(conversationName);
		return conversationDAO.addUser(us.getId(), cvs.getId());
	}

}
