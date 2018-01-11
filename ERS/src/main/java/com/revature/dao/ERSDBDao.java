package com.revature.dao;

import java.util.List;

import com.revature.model.ERSStatus;
import com.revature.model.ERSTypes;
import com.revature.model.Request;
import com.revature.model.Requests;
import com.revature.model.UpdateUser;
import com.revature.model.User;

public interface ERSDBDao {

	public ERSTypes getErsTypes();
	public ERSStatus getErsStatus();
	
	public boolean updateUserById(int userId, UpdateUser newInfo);
	public boolean selectByEmail(String email);
	public User getUserById(int userId);
	public User getUserByUsername(String user);
	//public boolean insertReimbursement();
	
	/*	Insert Request 
	 * 		u_amount IN NUMBER, 
	 * 		u_description IN VARCHAR2,
	 * 		u_receipt IN BLOB,
	 *		u_id IN NUMBER,
	 * 		u_type IN NUMBER
    */	
	public boolean insertRequest(Request userRequest);
	public Request getRequestById(int id);
	public Requests getRequestsByUserId(int id);
	public Requests getRequestsByStatus(int statusId);
	public Requests getRequestsByResolved();
	
	public List<User> getEmployees();
	public List<User> getDBUsers();
}
