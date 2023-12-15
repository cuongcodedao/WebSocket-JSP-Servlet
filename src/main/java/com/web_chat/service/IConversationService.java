package com.web_chat.service;

import java.util.List;

import javax.servlet.http.Part;

import com.web_chat.model.Conversation;

public interface IConversationService {
	public Conversation createNew(String usernameCreater, String group_name);
	public Conversation findByName(String name);
	public List<Conversation> findAllByUsername(String username);
	public int addUser(String username, String conversationName);
	public Conversation findById(int id);
	public void updateConversation(int id, String name, Part avatar);
}
