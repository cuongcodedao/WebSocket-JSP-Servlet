package com.web_chat.dao;

import java.util.List;

import com.web_chat.model.User;

public interface IFriendDAO {
	public int setFriend(int id1, int id2);
	public boolean findFriend(int id1, int id2);
	public List<User> findAllFriendOfUser(int id_user);
}
