package com.web_chat.controller.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web_chat.model.Conversation;
import com.web_chat.service.IConversationService;
import com.web_chat.service.impl.ConversationService;

@WebServlet(value = "/api_conversation")
public class ConversationRestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IConversationService conversationService = ConversationService.getInstance();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		if(username != null) {
			List<Conversation> listCVS = conversationService.findAllByUsername(username);
			ObjectMapper obj = new ObjectMapper();
			String listJson = obj.writeValueAsString(listCVS);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(listJson);
			writer.flush();
		}
		else if(name != null){
			Conversation cvs = conversationService.findByName(name);
			ObjectMapper obj = new ObjectMapper();
			String json = obj.writeValueAsString(cvs);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userAdded = request.getParameter("friend_name");
		String conversationName = request.getParameter("conversation_name");
		if(userAdded != null && conversationName != null) {
			conversationService.addUser(userAdded, conversationName);
		}
		else {
			BufferedReader br = request.getReader();
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = br.readLine())!=null) {
				sb.append(line);
			}
			String data = sb.toString();
			ObjectMapper mapper = new ObjectMapper();
			Conversation cvs = mapper.readValue(data, Conversation.class);
			cvs = conversationService.createNew(cvs.getUsernameCreater(), cvs.getName());
		}
		response.setContentType("application/json");
        response.getWriter().write("{\"status\": \"success\"}");
	}

}
