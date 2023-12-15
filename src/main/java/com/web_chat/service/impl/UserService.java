package com.web_chat.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.Part;

import com.web_chat.dao.IUserDAO;
import com.web_chat.dao.impl.UserDAO;
import com.web_chat.model.User;
import com.web_chat.service.FileAbstractService;
import com.web_chat.service.IUserService;

public class UserService implements IUserService{
	private static UserService instance = null;
	
	private IUserDAO userDAO = UserDAO.getInstance();

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

	@Override
	public User findUser(String username, String password) {
		return userDAO.findUser(username, password);
	}

	@Override
	public boolean Isonline(String username) {
		return userDAO.isOnline(username);
	}

	@Override
	public void setOnline(String username, int status) {
		userDAO.setOnline(username, status);
	}

	@Override
	public List<User> findUserOnline() {
		return userDAO.findUserOnline();
	}

	@Override
	public List<User> findAll() {
		return userDAO.findAll();
	}

	@Override
	public User findByUserName(String username) {
		return userDAO.findByUserName(username);
	}

	@Override
	public List<User> findAllByConversationId(int conversation_id) {
		return userDAO.findAllByConversationId(conversation_id);
	}

	@Override
	public User save(String username, String password, Part avt) {
		String filename = avt.getSubmittedFileName();
		File dir = new File(FileAbstractService.rootPath + "\\" + username);
		dir.mkdir();
		User user = null;
		if(filename != null) {
			try {
				String extension = filename.substring(filename.lastIndexOf('.'), filename.length());
				filename = "avatar"+extension;
				avt.write(dir.getPath() + "\\" + filename);
				System.out.println(filename);
				int id = userDAO.save(username, password, filename);
				user = userDAO.findById(id);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			int id = userDAO.save(username, password, "");
			user = userDAO.findById(id);
		}
		return user;
	}

	@Override
	public void updateUser(int id, String username, String password, Part avt) {
		String filename = avt.getSubmittedFileName();
		File dir = new File(FileAbstractService.rootPath + "\\" + username);
		if(!filename.equals("")) {
			try {
				String extension = filename.substring(filename.lastIndexOf('.'), filename.length());
				filename = "avatar"+extension;
				avt.write(dir.getPath() + "\\" + filename);
				userDAO.updateUser(id, username, password, filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			User user = userDAO.findByUserName(username);
			userDAO.updateUser(id, username, password, user.getAvatar());
		}
	}

	@Override
	public User findById(int id) {
		return userDAO.findById(id);
	}
	
}
