package com.revature.model;

public class LoginTest {

	private String username;
	private String name;
	
	
	
	public LoginTest(String username, String name) {
		super();
		this.username = username;
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "LoginTest [username=" + username + ", name=" + name + "]";
	}
	
	
}
