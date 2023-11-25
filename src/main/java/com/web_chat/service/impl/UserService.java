package com.web_chat.service.impl;

import java.util.List;

import com.web_chat.dao.IUserDAO;
import com.web_chat.dao.impl.UserDAO;
import com.web_chat.model.User;
import com.web_chat.service.IUserService;

public class UserService implements IUserService{
	private static UserService instance = null;
	
	private IUserDAO userDAO = UserDAO.getInstance();

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

	@Override
	public User findUser(String username, String password) {
		return userDAO.findUser(username, password);
	}

	@Override
	public boolean Isonline(String username) {
		return userDAO.isOnline(username);
	}

	@Override
	public void setOnline(String username, int status) {
		userDAO.setOnline(username, status);
	}

	@Override
	public List<User> findUserOnline() {
		return userDAO.findUserOnline();
	}

	@Override
	public List<User> findAll() {
		return userDAO.findAll();
	}

	@Override
	public User findByUserName(String username) {
		return userDAO.findByUserName(username);
	}

	@Override
	public List<User> findAllByConversationId(int conversation_id) {
		return userDAO.findAllByConversationId(conversation_id);
	}
	
}
