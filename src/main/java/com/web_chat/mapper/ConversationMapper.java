package com.web_chat.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.web_chat.model.Conversation;

public class ConversationMapper implements RowMapper<Conversation>{

	@Override
	public Conversation rowMapper(ResultSet rs) {
		Conversation cvs = new Conversation();
		try {
			cvs.setName(rs.getString("group__name"));
			cvs.setId(rs.getInt("id"));
			cvs.setAvatar(rs.getString("avatar"));
			cvs.setUsernameCreater(rs.getString("creater"));
			return cvs;
		} catch (SQLException e) {
			return null;
		}
	}
	
}
