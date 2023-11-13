package com.web_chat.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Message {
	private int id;
	private int conversation_id;
	private int user_id;
    private String from;
    private String to;
    private String content;
    private Timestamp date_sent;
    

    @Override
    public String toString() {
        return super.toString();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getConversation_id() {
		return conversation_id;
	}

	public void setConversation_id(int conversation_id) {
		this.conversation_id = conversation_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Timestamp getDate_sent() {
		return date_sent;
	}

	public void setDate_sent(Timestamp date) {
		this.date_sent = date;
	}
	
	public String hh_mm(){
		if(date_sent!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); 
			String formattedTime = sdf.format(date_sent);
			return formattedTime;
		}
		return "null";
		
	}
	
    
}
