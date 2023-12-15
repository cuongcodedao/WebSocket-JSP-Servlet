package com.web_chat.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.web_chat.model.User;

public class UserMapper implements RowMapper<User>{

	@Override
	public User rowMapper(ResultSet rs) {
		User user = new User();
		try {
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setOnline(rs.getBoolean("isonline"));
			user.setAvatar(rs.getString("avatar"));
			return user;
		} catch (SQLException e) {
			return null;
		}
	}
	
}
