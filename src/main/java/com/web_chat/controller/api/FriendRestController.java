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
import com.web_chat.model.Friend;
import com.web_chat.model.User;
import com.web_chat.service.IFriendService;
import com.web_chat.service.IMessageService;
import com.web_chat.service.impl.FriendService;
import com.web_chat.service.impl.MessageService;

@WebServlet(value = "/api_friend")
public class FriendRestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private IFriendService friendService = FriendService.getInstance();
	private IMessageService messageService = MessageService.getInstance();
    public FriendRestController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user1_id = request.getParameter("user1");
		String user2_id = request.getParameter("user2");
		String username = request.getParameter("username");
		if(user1_id != null && user2_id != null) {
			boolean check = friendService.findFriend(user1_id, user2_id);
			if(check == true) {
				response.setContentType("application/json");
		        response.getWriter().write("{\"status\": \"YES\"}");
			}
			else {
				response.setContentType("application/json");
		        response.getWriter().write("{\"status\": \"NO\"}");
			}
		}
		else if(username != null){
			List<User> users = friendService.findAllFriendOfUser(username);
			ObjectMapper object = new ObjectMapper();
			String json = object.writeValueAsString(users);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String requestData = stringBuilder.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        Friend friend = objectMapper.readValue(requestData, Friend.class);
        int check = friendService.setFriend(friend.getId_user1(), friend.getId_user2());
        if(check > 0 ) messageService.deleteMessageAddFriend(friend.getId_user1(), friend.getId_user2());
        response.setContentType("application/json");
        response.getWriter().write("{\"status\": \"success\"}");
	}

}
