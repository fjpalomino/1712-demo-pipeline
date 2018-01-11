package com.revature.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dto.LoggedInUser;
import com.revature.model.Request;
import com.revature.service.ERSService;

/**
 * Servlet implementation class AjaxProcessTxServlet
 */
@WebServlet("/processRequestInfo")
public class ProcessRequestInfo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1941947079968317612L;

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();			//variable with the object is the session
		LoggedInUser user = (LoggedInUser)session.getAttribute("user"); //the variable we used when the user logged in, in the login Servlet	
		
		//String reciept = request.getParameter("blob");
		//reciept = reciept.replaceFirst("^data:image/[^;]*;base64,?","");
		
		//byte[] decodedByte = Base64.getDecoder().decode(reciept);
		
		if (user!=null)
		{
			if(!(boolean)session.getAttribute("canSubmitRequest"))
			{
				//response.getWriter().println(reciept);
				response.setStatus(400);
			}
			else
			{	//set update session to false
				session.setAttribute( "canSubmitRequest", false );
				
				Request userRequest = (Request)session.getAttribute("request");
				
				ERSService service = new ERSService();
				
				userRequest.setId_author(user.getUSER_ID());
				
				if(service.submitRequest(userRequest))
				{
					response.getWriter().println("<div class='col-12 text-center'><h1>Your Request has been sent!</h1></div>");
				}
				else
				{
					//response.getWriter().println(recipt);
					response.setStatus(400);	//400 Bad Request
				}

			}	//<-----------------Check if user request session is active
		}		//<-----------------null check for session
	}
}
