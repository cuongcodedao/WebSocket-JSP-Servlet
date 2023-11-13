package com.web_chat.model;

import java.util.List;

public class Conversation {
	private int id;
	private String name;
	private String avatar;
	private List<User> users;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<User> getMessages() {
		return users;
	}

	public void setMessages(List<User> users) {
		this.users = users;
	}

}
