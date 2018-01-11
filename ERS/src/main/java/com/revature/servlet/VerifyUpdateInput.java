package com.revature.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.LoggedInUser;
import com.revature.model.UpdateUser;
import com.revature.service.ERSService;

@WebServlet("/verifyUpdateInput")
public class VerifyUpdateInput extends HttpServlet{

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
			UpdateUser newInfo = jackson.readValue(((String)obj), UpdateUser.class);
			
			ERSService service = new ERSService();
			
			if(newInfo.getUsername().equals(""))
			{
				response.getWriter().println("Username field is empty<script id='script'></script>");
			}
			else if(newInfo.getFirstName().equals(""))
			{
				response.getWriter().println("First name field is empty<script id='script'></script>");
			}
			else if(newInfo.getLastName().equals(""))
			{
				response.getWriter().println("Last name field is empty<script id='script'></script>");
			}
			else if(newInfo.getEmail().equals(""))
			{
				response.getWriter().println("Email field is empty<script id='script'></script>");
			}
			else if(newInfo.getUsername().contains(" "))
			{
				response.getWriter().println("Username should not have a space<script id='script'></script>");
			}
			//Check if username is taken...
			else if(!user.getUsername().equalsIgnoreCase(newInfo.getUsername()) && service.getUserInfoByUsername(newInfo.getUsername())!=null)
			{
				response.getWriter().println("Username not available<script id='script'></script>");
			}
			//check if email is correct format
			else if(!validateEmailString(newInfo.getEmail()))
			{
				response.getWriter().println("Email is not valid<script id='script'></script>");
			}
			//Check if email is in the database
			else if(!user.getEmail().equalsIgnoreCase(newInfo.getEmail()) && service.checkIfEmailExists(newInfo.getEmail()))
			{
				response.getWriter().println("Email is in use.<script id='script'></script>");
			}
			else
			{
				session.setAttribute("canUpdateUser", true);
				response.getWriter().println("<script id='script'>processUpdateInfo(updateInfo);</script>");
			}
		}
	}
	
	public boolean validateEmailString(String emailStr) {
		Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailRegex .matcher(emailStr);
        return matcher.find();
}

}
