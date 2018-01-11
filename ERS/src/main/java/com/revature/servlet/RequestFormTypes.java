package com.revature.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.ERSTypes;
import com.revature.service.ERSService;

@WebServlet("/requestFormTypes")
public class RequestFormTypes extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6188353019469924738L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ERSService service = new ERSService();		
		ERSTypes ersTypes = service.getTypes();
		
		ObjectMapper mapper = new ObjectMapper();		
		String json = mapper.writeValueAsString(ersTypes);
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.write(json);
	}

}
