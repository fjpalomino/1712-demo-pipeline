package com.revature.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.LoggedInUser;

@WebServlet("/myAccountInfo")
public class MyAccountInfo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1707610261784788021L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//ERSService service = new ERSService();	maybe needed
		HttpSession session = request.getSession();
		LoggedInUser sessionUser = (LoggedInUser) session.getAttribute("user");

		if(sessionUser != null){
			
			ObjectMapper mapper = new ObjectMapper();
			
			String json = mapper.writeValueAsString(sessionUser);
			
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.write(json);

		}else{
			response.setStatus(401);
		}
	}
}
