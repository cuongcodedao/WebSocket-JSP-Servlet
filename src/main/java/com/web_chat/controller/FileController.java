package com.web_chat.controller;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.web_chat.model.Conversation;
import com.web_chat.model.User;
import com.web_chat.service.IConversationService;
import com.web_chat.service.IUserService;
import com.web_chat.service.impl.ConversationService;
import com.web_chat.service.impl.UserService;

@WebServlet(value = "/files/*")
@MultipartConfig(
		  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		  maxFileSize = 1024 * 1024 * 10,      // 10 MB
		  maxRequestSize = 1024 * 1024 * 100   // 100 MB
		)
public class FileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String rootPath = "C:\\Users\\DANG VAN CUONG\\eclipse-workspace\\web_chat\\archive";
	private IConversationService conversationService = ConversationService.getInstance();
	private IUserService userService = UserService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sender = request.getParameter("sender"); 
        String receiver = request.getParameter("receiver");
        String namefile = request.getParameter("filename");
        String userId = request.getParameter("userId");
        String conversationId = request.getParameter("conversationId");
        if(receiver != null) {
        	File file = new File(rootPath + "\\" + sender + "_" + receiver + "\\" + namefile);
        	FileInputStream fis = new FileInputStream(file);
        	if(namefile.contains(".mp4")) {
        		 response.setContentType("video/mp4");
        	}
        	else if(namefile.contains(".pdf")) {
        		 response.setContentType("application/pdf");
        	}
        	else response.setContentType("image/jpeg");
            response.setContentLength((int) file.length());
           
            OutputStream os = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        }
        else if(userId != null){
        	int id = Integer.parseInt(userId);
        	User user = userService.findById(id);
        	File file = new File(rootPath + "\\" + user.getUsername() + "\\" + user.getAvatar());
        	FileInputStream fis = new FileInputStream(file);
        	response.setContentType("image/jpeg");
            response.setContentLength((int) file.length());
           
            OutputStream os = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        }
        else if(conversationId!=null) {
        	int id = Integer.parseInt(conversationId);
        	Conversation cv = conversationService.findById(id);
        	File file = new File(rootPath + "\\conversation_avatar\\" + conversationId + "\\" + cv.getAvatar());
        	FileInputStream fis = new FileInputStream(file);
        	response.setContentType("image/jpeg");
            response.setContentLength((int) file.length());
           
            OutputStream os = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        }
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
}
