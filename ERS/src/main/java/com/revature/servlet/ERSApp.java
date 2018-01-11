package com.revature.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dto.LoggedInUser;

@WebServlet("/ERSApp")
public class ERSApp extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1707610261784788021L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		LoggedInUser sessionUser = (LoggedInUser) session.getAttribute("user");

		if(sessionUser != null){
			request.getRequestDispatcher("ERSApp.html").forward(request, response);
		}
	}
}