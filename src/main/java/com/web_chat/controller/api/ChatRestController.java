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
import com.web_chat.model.Message;
import com.web_chat.service.IMessageService;
import com.web_chat.service.impl.MessageService;

@WebServlet(value = "/api_chat")
public class ChatRestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IMessageService messageService = MessageService.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sender = request.getParameter("sender");
		String receiver = request.getParameter("receiver");
		List<Message> messages = messageService.findAllMessageBySenderAndReceiver(sender, receiver);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(messages);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
