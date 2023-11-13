package com.web_chat.dao.impl;

import java.util.List;

import com.web_chat.dao.IMessageDAO;
import com.web_chat.mapper.MessageMapper;
import com.web_chat.model.Message;

public class MessageDAO extends AbstractDAO<Message> implements IMessageDAO{
	
	private static MessageDAO instance = null;

    private MessageDAO() {
        
    }

    public static synchronized MessageDAO getInstance() {
        if (instance == null) {
            instance = new MessageDAO();
        }
        return instance;
    }

	@Override
	public List<Message> findByUsername(String username) {
		String sql = "select * from message where username = ?";
		return query(sql, new MessageMapper(), username);
	}

	@Override
	public List<Message> findAll() {
		String sql = "select * from message";
		return query(sql, new MessageMapper());
	}

	@Override
	public int save(Message message) {
		String sql = "insert into message(`from`, `to`, content, date_sent) values(?,?,?,?)";
		return save(sql, message.getFrom(), message.getTo(), message.getContent(), message.getDate_sent());
	}

	@Override
	public Message findById(int id) {
		String sql = "select * from message where id = ?";
		List<Message> list = query(sql, new MessageMapper(), id);
		return list.get(0);
	}

	@Override
	public List<Message> getAllMessageBySenderandReceiver(String sender, String receiver) {
		String sql = "select * from message where (`from` = ? and `to` = ?) or (`from` = ? and `to` = ?)";
		return query(sql, new MessageMapper() ,sender, receiver, receiver, sender);
	}
	
	

	
}
