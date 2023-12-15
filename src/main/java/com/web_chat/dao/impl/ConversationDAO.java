package com.web_chat.dao.impl;

import java.util.List;

import com.web_chat.dao.IConversationDAO;
import com.web_chat.mapper.ConversationMapper;
import com.web_chat.mapper.UserMapper;
import com.web_chat.model.Conversation;

public class ConversationDAO extends AbstractDAO<Conversation> implements IConversationDAO{
	private static ConversationDAO instance = null;
	
	private ConversationDAO() {
	}
	public static synchronized ConversationDAO getInstance() {
		if(instance == null) {
			instance = new ConversationDAO();
		}
		return instance;
	}

	@Override
	public Conversation createNew(String usernameCreater, String group_name) {
		String sql = "insert into conversation(group__name, creater) values(?, ?)";
		int id = save(sql, group_name, usernameCreater);
		return findById(id);
	}

	@Override
	public int addUser(int user_id, int conversation_id) {
		if(!checkUserInGroup(user_id, conversation_id)) {
			String sql = "insert into conversation_user(conversation_id, user_id) values(?, ?)";
			return save(sql, conversation_id, user_id);
		}
		return 0;
	}

	@Override
	public Conversation findById(int id) {
		String sql = "select * from conversation where id = ?";
		List<Conversation> list = query(sql, new ConversationMapper(), id);
		return list.size()>0 ? list.get(0):null;
	}
	@Override
	public List<Conversation> findAllByUsername(String username) {
		StringBuilder sb = new StringBuilder("select conversation.* from conversation ");
		sb.append("inner join conversation_user on conversation.id = ");
		sb.append("conversation_user.conversation_id inner join ");
		sb.append("user on conversation_user.user_id = user.id ");
		sb.append("where user.username = ?");
		return query(sb.toString(), new ConversationMapper(), username);
	}
	@Override
	public Conversation findByName(String name) {
		String sql = "select * from conversation where group__name = ?";
		List<Conversation> list = query(sql, new ConversationMapper(), name);
		return list.size()>0 ? list.get(0) : null;
	}
	@Override
	public boolean checkUserInGroup(int user_id, int conversation_id) {
		String sql = "select user.* from user inner join conversation_user "
				+ "on conversation_user.user_id = user.id where conversation_user.conversation_id = ? and "
				+ "conversation_user.user_id = ?";
		return query(sql, new UserMapper() ,conversation_id, user_id).size()>0 ? true:false;
	}
	@Override
	public void updateConversation(Conversation cvs) {
		String sql = "update conversation set group__name = ?, avatar = ? where id = ?";
		update(sql, cvs.getName(), cvs.getAvatar(), cvs.getId());
	}
	
}
