package com.web_chat.service;

import java.util.List;

import javax.servlet.http.Part;

import com.web_chat.model.User;

public interface IUserService {
	public User findById(int id);
	public User save(String username, String password, Part avatar);
	public User findUser(String username, String password);
	public boolean Isonline(String username);
	public void setOnline(String username, int status);
	public List<User> findUserOnline();
	public List<User> findAll();
	public User findByUserName(String username);
	public List<User> findAllByConversationId(int conversation_id);
	public void updateUser(int id, String username, String password, Part avatar);
}
