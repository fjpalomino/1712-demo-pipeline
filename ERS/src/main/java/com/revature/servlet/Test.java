package com.revature.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dto.LoggedInUser;
import com.revature.model.User;
import com.revature.service.ERSService;

@WebServlet(urlPatterns = {"/verify"})
public class Test extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6921131630529982390L;
	
	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.sendRedirect("login");
	}
	
	@Override 
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String usr = req.getParameter("usr");
		String pass = req.getParameter("pass");
		
		HttpSession session = req.getSession(true);
		ERSService service = new ERSService();		//my system services
		
		if(!usr.equals("") && !pass.equals(""))
		{
			if(service.validateUser(usr,pass)!=null)
			{
				User userInfo = service.getUserInfoByUsername(usr);
				LoggedInUser userDTO = service.convertToUserDTO(userInfo);
				
				session.setAttribute( "role", userInfo.getUSER_ROLE() );			//used to define session type
				session.setAttribute( "user", userDTO);	//set users info on the session variable
				
				resp.getWriter().println("<script id='script'>window.location = 'ERSApp';</script>");
			}
			//User password failed
			else
			{
				resp.getWriter().println("Username or password incorrect"
						+ "<script id='script'>document.getElementById('usr').select();"
						+ "document.getElementById('pass').value='';</script>");
			}
		}
		else
		{
			resp.getWriter().println("Username or password empty"
					+ "<script id='script'>document.getElementById('usr').select();"
					+ "document.getElementById('pass').value='';</script>");
		}
	}

}
