package com.web_chat.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.web_chat.model.User;
import com.web_chat.service.IUserService;
import com.web_chat.service.impl.UserService;

/**
 * Servlet implementation class UserController
 */
@WebServlet(value = "/user/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 50, // 50MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserController() {
		super();
	}
	private IUserService userService = UserService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String warning = request.getParameter("warning");
		String pathInfo = "register";
		if(username!=null) { 
			User user = userService.findByUserName(username);
			request.setAttribute("user", user);
			pathInfo = "update";
		}
		request.setAttribute("pathInfo", pathInfo);
		String desPage = "views/user_form.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(desPage);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setContentType("text/html;charset=UTF-8");
			String path = request.getRequestURI();
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			Part avt = request.getPart("avatar");
			if(path.contains("register")) {
				User checkInfo = userService.findByUserName(username);
				if(checkInfo != null) {
					response.sendRedirect("/user");
				}
				else {
					User user = userService.save(username, password, avt);
					if(user!=null) {
						response.sendRedirect("/login");
					}
					else {
						response.sendRedirect("/user");
					}
				}
			}
			else if(path.contains("update")){
				User user = userService.findByUserName(username);
				if(user!=null) {
					userService.updateUser(user.getId(),username, password, avt);
					response.sendRedirect("/chat_box");
				}
				else {
					response.sendRedirect("/user/update");
				}
			}
			
	}

}
