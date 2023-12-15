package com.web_chat.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.web_chat.model.User;
import com.web_chat.service.IUserService;
import com.web_chat.service.impl.UserService;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private IUserService userService = UserService.getInstance();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		if(user != null) {
			response.sendRedirect("/chat_box");
		}
		else {
			RequestDispatcher rd = request.getRequestDispatcher("/views/login.jsp");
			rd.forward(request, response);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userService.findUser(username, password);
		String destPage = "/login";
		if(user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			destPage = "/chat_box";
		}
		response.sendRedirect(destPage);
	}

}
