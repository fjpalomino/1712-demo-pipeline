package com.revature.model;

public class User {
	private int USER_ID;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int USER_ROLE;
	
	public User() {}
	
	public User(int uSER_ID, String username,String password, String firstName, String lastName, String email, int uSER_ROLE) {
		super();
		USER_ID = uSER_ID;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		USER_ROLE = uSER_ROLE;
	}
	
	public int getUSER_ID() {
		return USER_ID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getUSER_ROLE() {
		return USER_ROLE;
	}

	@Override
	public String toString() {
		return "User [USER_ID=" + USER_ID + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", USER_ROLE=" + USER_ROLE + "]";
	}


	
}
