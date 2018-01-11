package com.revature.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dto.LoggedInUser;

@WebServlet("/requestFormPage")
public class RequestFormPage extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6188353019469924738L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		LoggedInUser user = (LoggedInUser)session.getAttribute("user");
		
		if (user!=null)
		{
			request.getRequestDispatcher("features/request.html").forward(request, response);
		}
	}

}
