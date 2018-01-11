package com.revature.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dto.LoggedInUser;

@WebServlet("/viewEmployeesPage")
public class ViewEmployeesPage extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8275721061946604937L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		LoggedInUser user = (LoggedInUser)session.getAttribute("user");
		
		if (user!=null)
		{
			if((int)session.getAttribute("role")==1)
				request.getRequestDispatcher("features/viewEmployees.html").forward(request, response);
		}
	}

}
