package com.web_chat.service;

import java.util.List;

import com.web_chat.model.User;

public interface IFriendService {
	public int setFriend(String username1, String username2);
	public boolean findFriend(String username1, String username2);
	public List<User> findAllFriendOfUser(String username);
}
