package com.revature.model;

import java.util.ArrayList;

public class Requests {

	public ArrayList<Request> requests;

	public Requests(){};
	
	public Requests(ArrayList<Request> requests) {
		super();
		this.requests = requests;
	}
	
	public void setRequests(ArrayList<Request> requests) {
		this.requests = requests;
	}

	public ArrayList<Request> getRequests() {
		return requests;
	}	
}
