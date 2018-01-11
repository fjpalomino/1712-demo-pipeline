package com.revature.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.model.User;
import com.revature.service.ERSService;

@WebServlet("/verify2")
public class Verify extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2607919482363552869L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//nothing sent
		resp.sendRedirect("/ERSApp");		
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
				session.setAttribute( "role", userInfo.getUSER_ROLE() );			//used to define session type
				session.setAttribute( "user", userInfo);	//set users info on the session variable
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
