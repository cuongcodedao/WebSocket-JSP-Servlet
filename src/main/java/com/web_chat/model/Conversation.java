package com.web_chat.model;

public class Conversation {
	private int id;
	private String name;
	private String avatar;
	private String usernameCreater;

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

	public String getUsernameCreater() {
		return usernameCreater;
	}

	public void setUsernameCreater(String usernameCreater) {
		this.usernameCreater = usernameCreater;
	}

}
