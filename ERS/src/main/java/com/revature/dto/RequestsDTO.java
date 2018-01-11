package com.revature.dto;

import java.util.ArrayList;


public class RequestsDTO {
	public ArrayList<RequestDTO> requests;

	public RequestsDTO(){};
	
	public RequestsDTO(ArrayList<RequestDTO> requests) {
		this.requests = requests;
	}
	
	public void setRequests(ArrayList<RequestDTO> requests) {
		this.requests = requests;
	}

	public ArrayList<RequestDTO> getRequests() {
		return requests;
	}	
}
