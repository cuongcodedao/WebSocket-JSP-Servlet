package com.web_chat.dao;

import java.util.List;

import com.web_chat.model.User;

public interface IUserDAO {
	public User findUser(String username, String password);
	public boolean isOnline(String username);
	public void setOnline(String username, int status);
	public List<User> findUserOnline();
	public List<User> findAll();
	public User findByUserName(String username);
}
