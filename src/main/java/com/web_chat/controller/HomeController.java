package com.web_chat.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web_chat.model.Message;
import com.web_chat.model.User;
import com.web_chat.service.IMessageService;
import com.web_chat.service.IUserService;
import com.web_chat.service.impl.MessageService;
import com.web_chat.service.impl.UserService;

/**

 */
@WebServlet(value = "/chat_box")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IMessageService messageService = MessageService.getInstance();
	private IUserService userService = UserService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Message> list = messageService.findAll();
		List<User> listUser = userService.findAll();
		request.setAttribute("messages", list);
		request.setAttribute("users", listUser);
		RequestDispatcher rd = request.getRequestDispatcher("/views/chat_box.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
