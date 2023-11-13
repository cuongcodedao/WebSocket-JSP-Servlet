package com.web_chat.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.web_chat.model.Message;

public class MessageMapper implements RowMapper<Message>{

	@Override
	public Message rowMapper(ResultSet rs) {
		Message message = new Message();
		try {
			message.setConversation_id(rs.getInt("conversation_id"));
			message.setId(rs.getInt("id"));
			message.setUser_id(rs.getInt("user_id"));
			message.setFrom(rs.getString("from"));
			message.setTo(rs.getString("to"));
			message.setContent(rs.getString("content"));
//			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//            String formattedDate = dateFormat.format(date);
			message.setDate_sent(rs.getTimestamp("date_sent"));
			return message;
		} catch (SQLException e) {
			return null;
		}
	}
	
}
