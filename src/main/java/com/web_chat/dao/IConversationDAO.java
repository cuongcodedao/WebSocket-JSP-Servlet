package com.web_chat.dao;

import java.util.List;

import com.web_chat.model.Conversation;

public interface IConversationDAO {
	public Conversation findById(int id);
	public Conversation findByName(String name);
	public List<Conversation> findAllByUsername(String username);
	public Conversation createNew(String usernameCreater, String group_name);
	public int addUser(int user_id, int conversation_id);
	public boolean checkUserInGroup(int user_id, int conversation_id);
	public void updateConversation(Conversation cvs);
}
