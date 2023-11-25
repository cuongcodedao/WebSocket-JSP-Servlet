package com.web_chat.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.web_chat.model.Friend;

public class FriendMapper implements RowMapper<Friend>{

	@Override
	public Friend rowMapper(ResultSet rs) {
		Friend friend = new Friend();
		try {
			friend.setId(rs.getInt("id"));
			friend.setId_user1("user1_id");
			friend.setId_user2("user2_id");
			return friend;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
