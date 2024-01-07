package com.web_chat.dao;

import java.util.List;

import com.web_chat.model.User;

public interface IUserDAO {
	public User findById(int id);
	public void updateUser(int id, String username, String password, String avatar);
	public int save(String username, String password, String avatar);
	public User findUser(String username, String password);
	public boolean isOnline(String username);
	public void setOnline(String username, int status);
	public List<User> findUserOnline();
	public List<User> findAll();
	public List<User> findByKeyName(String keyname);
	public List<User> findAllByConversationId(int conversation_id);
	public User findByUserName(String username);
}
