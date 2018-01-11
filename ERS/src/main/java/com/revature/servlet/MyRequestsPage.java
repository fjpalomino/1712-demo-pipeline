package com.revature.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dto.LoggedInUser;

@WebServlet("/myRequestsPage")
public class MyRequestsPage extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1707610261784788021L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		LoggedInUser user = (LoggedInUser)session.getAttribute("user");
		
		if (user!=null)
		{
			request.getRequestDispatcher("features/myRequests.html").forward(request, response);
		}
	}
}
