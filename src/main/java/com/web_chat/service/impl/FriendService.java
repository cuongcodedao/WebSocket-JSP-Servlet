package com.web_chat.service.impl;

import java.util.List;

import com.web_chat.dao.IFriendDAO;
import com.web_chat.dao.IUserDAO;
import com.web_chat.dao.impl.FriendDAO;
import com.web_chat.dao.impl.UserDAO;
import com.web_chat.model.User;
import com.web_chat.service.IFriendService;

public class FriendService implements IFriendService{
	private static FriendService instance = null;
	
	private FriendService() {
	}
	
	private IFriendDAO friendDAO = FriendDAO.getInstance();
	private IUserDAO userDao = UserDAO.getInstance();
	
	public static synchronized FriendService getInstance() {
		if(instance == null) {
			instance = new FriendService();
		}
		return instance;
	}
	@Override
	public int setFriend(String username1, String username2) {
		User user1 = userDao.findByUserName(username1);
		User user2 = userDao.findByUserName(username2);
		return friendDAO.setFriend(user1.getId(), user2.getId());
	}
	@Override
	public boolean findFriend(String username1, String username2) {
		User user1 = userDao.findByUserName(username1);
		User user2 = userDao.findByUserName(username2);
		return friendDAO.findFriend(user1.getId(), user2.getId());
	}
	@Override
	public List<User> findAllFriendOfUser(String username) {
		User user = userDao.findByUserName(username);
		return friendDAO.findAllFriendOfUser(user.getId());
	}

}
