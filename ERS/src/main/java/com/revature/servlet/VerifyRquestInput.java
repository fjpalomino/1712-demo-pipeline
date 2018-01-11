package com.revature.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.LoggedInUser;
import com.revature.model.Request;

@WebServlet("/verifyRequestInput")
public class VerifyRquestInput extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5118451020707950329L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		LoggedInUser user = (LoggedInUser)session.getAttribute("user");
		
		if (user!=null)
		{
			//Grab all paramenters, in this case only 1 JSON String
			Map<String, String[]> myMap = request.getParameterMap();
		
			//Get the the keySet from the map, returns a !!!!Set the name fields of the post!!!!!!!!
			Set<String> txObject = myMap.keySet();
			
			//Convert the the keySet into an array, then get the first element (index 0) from that set
			Object obj = txObject.toArray()[0];
			
			//API for converting our JSON into a Java Object
			ObjectMapper jackson = new ObjectMapper();
				
			//Convert the JSON String into the Class specified in the second argument
			Request userRequest = jackson.readValue(((String)obj), Request.class);
			
			//ERSService service = new ERSService();
			
			if(userRequest.getAmount() <= 0)
			{
				response.getWriter().println("Verify amount<script id='script'></script>");
			}
			else if(userRequest.getDescription().equals(""))
			{
				response.getWriter().println("Please provide a description<script id='script'></script>");
			}
			else if(userRequest.getRtType() <= 0)
			{
				response.getWriter().println("Please select a Type of Reimbursement<script id='script'></script>");
			}
			else
			{
				session.setAttribute( "request", userRequest);
				session.setAttribute("canSubmitRequest", true);
				response.getWriter().println("<script id='script'>processRequestInfo();</script> ");
			}
		}
	}

}
