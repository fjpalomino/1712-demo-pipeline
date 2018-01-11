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
import com.revature.model.UpdateUser;
import com.revature.model.User;
import com.revature.service.ERSService;

/**
 * Servlet implementation class AjaxProcessTxServlet
 */
@WebServlet("/processUpdateInfo")
public class ProcessUpdateInfo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1941947079968317612L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();			//variable with the object is the session
		LoggedInUser user = (LoggedInUser)session.getAttribute("user"); //the variable we used when the user logged in, in the login Servlet
		
		if (user!=null)
		{
			if(!(boolean)session.getAttribute("canUpdateUser"))
			{
				response.setStatus(400);
			}
			else
			{	//set update session to false
				session.setAttribute( "canUpdateUser", false );
				//Grab all paramenters, in this case only 1 JSON String
				Map<String, String[]> myMap = request.getParameterMap();
			
				//Get the the keySet from the map, returns a !!!!Set the name fields of the post!!!!!!!!
				Set<String> txObject = myMap.keySet();
				
				//Convert the the keySet into an array, then get the first element (index 0) from that set
				Object obj = txObject.toArray()[0];
				
				//API for converting our JSON into a Java Object
				ObjectMapper jackson = new ObjectMapper();
					
				//Convert the JSON String into the Class specified in the second argument
				UpdateUser newInfo = jackson.readValue(((String)obj), UpdateUser.class);
				
				if(user.getUSER_ID() == newInfo.getUserId())
				{
					if(!user.getUsername().equals(newInfo.getUsername())
						|| !user.getFirstName().equals(newInfo.getFirstName())
						|| !user.getLastName().equals(newInfo.getLastName())
						|| !user.getEmail().equals(newInfo.getEmail()))
					{
						ERSService service = new ERSService();
						User userInfo = service.updateUserInfo(user.getUSER_ID(), newInfo);
						user = service.convertToUserDTO(userInfo);
						if(user!=null)
						{
							session.setAttribute( "user", user);
							response.getWriter().println("<div class='col-12 text-center'><h1>Your Information has been Updated!</h1></div>"
									+ "<div class='col-12 text-center'><button class='btn btn-primary' id='refreshAccount'>View Account</button></div>");
						}
						else
						{
							response.setStatus(400);	//400 Bad Request
						}
					}
					else
					{
						response.getWriter().println("<div class='col-12 text-center'><h1>No change detected.</h1></div>"
								+ "<div class='col-12 text-center'><button class='btn btn-primary' id='refreshAccount'>View Account</button></div>");
					}
				}
				else
				{
					response.setStatus(400);	//id was lost???!!! 400 Bad Request			
				}
			}	//<-----------------Check if user update session is active
		}		//<-----------------null check for session
	}
}
