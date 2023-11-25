package com.web_chat.dao.impl;

import java.util.List;

import com.web_chat.dao.IUserDAO;
import com.web_chat.mapper.ConversationMapper;
import com.web_chat.mapper.MessageMapper;
import com.web_chat.mapper.UserMapper;
import com.web_chat.model.User;


public class UserDAO extends AbstractDAO<User> implements IUserDAO{
	
	private static UserDAO instance = null;

    private UserDAO() {
        
    }

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

	@Override
	public User findUser(String username, String password) {
		String sql = "select * from user where username = ? and `password`=?";
		List<User> list = query(sql, new UserMapper() ,username, password);
		return list.size() == 0 ? null : list.get(0);
	}

	@Override
	public boolean isOnline(String username) {
		String sql = "select * form user where username = ?";
		List<User> list = query(sql, new UserMapper() ,username);
		return list.get(0).isOnline();
	}
	
	public void setOnline(String username, int status) {
		String sql = "update user set isonline = ? where username = ?";
		update(sql, status, username);
	}

	@Override
	public List<User> findUserOnline() {
		String sql = "select * user where isonline = 1";
		return query(sql, new UserMapper());
	}

	@Override
	public List<User> findAll() {
		String sql = "select * from user";
		return query(sql, new UserMapper());
	}

	@Override
	public User findByUserName(String username) {
		String sql = "select * from user where username = ?";
		List<User> users = query(sql, new UserMapper(), username);
		return users.size() > 0 ? users.get(0) : null;
	}

	@Override
	public List<User> findAllByConversationId(int conversation_id) {
		StringBuilder sb = new StringBuilder("select user.* from user ");
		sb.append("inner join conversation_user on user.id = ");
		sb.append("conversation_user.user_id inner join ");
		sb.append("conversation on conversation_user.conversation_id = conversation.id ");
		sb.append("where conversation.id = ?");
		return query(sb.toString(), new UserMapper(), conversation_id);
	}
	
	
}
