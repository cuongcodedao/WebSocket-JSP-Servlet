package com.web_chat.dao.impl;

import java.util.List;

import com.web_chat.dao.IFriendDAO;
import com.web_chat.mapper.FriendMapper;
import com.web_chat.mapper.UserMapper;
import com.web_chat.model.Friend;
import com.web_chat.model.User;

public class FriendDAO extends AbstractDAO<Friend> implements IFriendDAO{
	private static FriendDAO instance = null;
	private FriendDAO() {
		
	}
	public static synchronized FriendDAO getInstance() {
		if(instance == null) {
			instance = new FriendDAO();
		}
		return instance;
	}

	@Override
	public int setFriend(int id1, int id2) {
		if(id1 > id2) {
			int tmp = id1;
			id1 = id2;
			id2 = tmp;
		}
		String sql = "insert into friends(user1_id, user2_id) values (?,?) ";
		return save(sql, id1, id2);
	}
	@Override
	public boolean findFriend(int id1, int id2) {
		if(id1 > id2) {
			int tmp = id1;
			id1 = id2;
			id2 = tmp;
		}
		String sql = "select * from friends where user1_id = ? and user2_id = ?";
		List<Friend> list = query(sql, new FriendMapper(), id1, id2);
		return list.size() >0  ? true : false;
	}
	@Override
	public List<User> findAllFriendOfUser(int id_user) {
		StringBuilder sb = new StringBuilder("select * from user ");
		sb.append("where id in (select case when user1_id = ? then user2_id ");
		sb.append("else user1_id end as friend_id from friends ");
		sb.append("WHERE user1_id = ? OR user2_id = ?)");
		return query(sb.toString(), new UserMapper(), id_user, id_user, id_user);
	}
}
