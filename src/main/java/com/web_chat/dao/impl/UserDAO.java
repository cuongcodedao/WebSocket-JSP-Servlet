package com.web_chat.dao.impl;

import java.util.List;

import com.web_chat.dao.IUserDAO;
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
		for(User u:list) {
			System.out.print(u);
		}
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

	@Override
	public int save(String username, String password, String avatar) {
		String sql = "insert into user(username, password, isonline, avatar) values(?,?,?,?)";
		return save(sql, username, password, 0, avatar);
	}

	@Override
	public User findById(int id) {
		String sql = "select * from user where id = ?";
		List<User> list = query(sql, new UserMapper(), id);
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public void updateUser(int id, String username, String password, String avatar) {
		String sql = "update user set username = ?, password = ?, avatar = ? where id = ?";
		update(sql, username, password, avatar, id);
	}

	@Override
	public List<User> findByKeyName(String keyname) {
		String sql = "select * from user where username like '"+keyname+"%'";
		List<User> list = query(sql, new UserMapper(), keyname);
		return list;
	}
	
	
}
