package com.web_chat.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.web_chat.dao.IConversationDAO;
import com.web_chat.model.Conversation;
import com.web_chat.service.IConversationService;
import com.web_chat.service.impl.ConversationService;

@WebServlet("/conversation")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 50, // 50MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ConversationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ConversationController() {
		super();

	}

	private IConversationService conversationService = ConversationService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String conversationId = request.getParameter("conversationId");
		Conversation cvs = conversationService.findById(Integer.parseInt(conversationId));
		String desPage = "views/conversation_form.jsp";
		if (cvs == null) {
			desPage = "views/chat_box.jsp";
		}
		request.setAttribute("conversation", cvs);
		RequestDispatcher rd = request.getRequestDispatcher(desPage);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String groupName = request.getParameter("name");
		Part avatar = request.getPart("avatar");
		String id = request.getParameter("conversation_id");
		int cvs_id = Integer.parseInt(id);
		Conversation con = conversationService.findById(cvs_id);
		Conversation cvs = conversationService.findByName(groupName);
		if (!groupName.equals(con.getName())) {
			if (cvs == null) {
				conversationService.updateConversation(con.getId(), groupName, avatar);
				response.sendRedirect("/chat_box");
			} else {
				response.sendRedirect("/conversation");
			}
		} else {
			conversationService.updateConversation(con.getId(), groupName, avatar);
			response.sendRedirect("/chat_box");
		}
	}

}
