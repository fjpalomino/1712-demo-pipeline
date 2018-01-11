package com.revature.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.LoggedInUser;
import com.revature.dto.RequestsDTO;
import com.revature.model.Requests;
import com.revature.service.ERSService;

@WebServlet("/resolvedRequests")
public class ResolvedRequests extends HttpServlet{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1860225982028025007L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		LoggedInUser sessionUser = (LoggedInUser) session.getAttribute("user");
		
		if(sessionUser != null){
			
			ERSService service = new ERSService();
			Requests userRequests = service.getResolvedRequests();
			RequestsDTO rDto = service.convertToRequestsDTO(userRequests);
			
			ObjectMapper mapper = new ObjectMapper();
			
			String json = mapper.writeValueAsString(rDto);
			
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.write(json);

		}else{
			response.setStatus(401);
		}
		
		
	}
}
