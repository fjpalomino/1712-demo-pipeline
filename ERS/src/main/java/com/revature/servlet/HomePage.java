package com.revature.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dto.LoggedInUser;

@WebServlet("/homePage")
public class HomePage extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1707610261784788021L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		LoggedInUser user = (LoggedInUser)session.getAttribute("user");
		
		if (user!=null)
		{
			if((int)session.getAttribute("role")==1)
				request.getRequestDispatcher("features/managerHome.html").forward(request, response);
			else if((int)session.getAttribute("role")==2)
				request.getRequestDispatcher("features/home.html").forward(request, response);
		}
		
	}
}