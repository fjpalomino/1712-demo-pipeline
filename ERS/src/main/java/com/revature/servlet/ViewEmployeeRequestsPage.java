package com.revature.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dto.LoggedInUser;

@WebServlet("/viewEmployeeRequestsPage")
public class ViewEmployeeRequestsPage extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8275721061946604937L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("employeeId");
		
		HttpSession session = request.getSession(true);
		LoggedInUser user = (LoggedInUser)session.getAttribute("user");
				
		session.setAttribute("employeeId", id );
		
		if (user!=null && id!=null)
		{
			if((int)session.getAttribute("role")==1)
				request.getRequestDispatcher("features/employeeRequests.html").forward(request, response);
		}
	}

}
