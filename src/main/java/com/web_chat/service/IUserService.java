package com.web_chat.service;

import java.util.List;

import com.web_chat.model.User;

public interface IUserService {
	public User findUser(String username, String password);
	public boolean Isonline(String username);
	public void setOnline(String username, int status);
	public List<User> findUserOnline();
	public List<User> findAll();
	public User findByUserName(String username);
	public List<User> findAllByConversationId(int conversation_id);
}
