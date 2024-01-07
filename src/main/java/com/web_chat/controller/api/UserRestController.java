package com.web_chat.controller.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web_chat.model.User;
import com.web_chat.service.IUserService;
import com.web_chat.service.impl.UserService;
@WebServlet(value ="/api_user")
public class UserRestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IUserService userService = UserService.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String username = request.getParameter("username");
		String keyname = request.getParameter("keyname");
		if(username != null) {
			User user = userService.findByUserName(username);
			String json = objectMapper.writeValueAsString(user);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
		}
		else if(keyname!=null) {
			List<User> users = userService.findByKeyName(keyname);
			String json = objectMapper.writeValueAsString(users);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
		}
		else {
			List<User> listUser = userService.findAll();
			String json = objectMapper.writeValueAsString(listUser);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
