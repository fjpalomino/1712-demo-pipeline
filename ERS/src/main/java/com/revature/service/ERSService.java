package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.ERSDBDao;
import com.revature.dao.ERSDBDaoImpl;
import com.revature.dto.EmployeesDTO;
import com.revature.dto.LoggedInUser;
import com.revature.dto.RequestDTO;
import com.revature.dto.RequestsDTO;
import com.revature.model.ERSStatus;
import com.revature.model.ERSTypes;
import com.revature.model.Request;
import com.revature.model.Requests;
import com.revature.model.UpdateUser;
import com.revature.model.User;

public class ERSService {
	
	ERSDBDao ersAccess = new ERSDBDaoImpl();
	
	public ERSTypes getTypes()
	{
		return ersAccess.getErsTypes();
	}
	public ERSStatus getStatus()
	{
		return ersAccess.getErsStatus();
	}
	
	/**
	 * 	Get User information by his ID
	 * @param userId
	 * @return
	 */
	public User getUserInfoById(int userId){
		return ersAccess.getUserById(userId);
	}
	public boolean checkIfEmailExists(String email){
		return ersAccess.selectByEmail(email.toLowerCase());
	}
	public User getUserInfoByUsername(String username){
		return ersAccess.getUserByUsername(username.toLowerCase());
	}
	
	public User validateUser(String usr, String pass)
	{
		User dbUser = getUserInfoByUsername(usr.toLowerCase());
		
		if(dbUser!=null){
			if(dbUser.getPassword().equals(pass)){
				return dbUser;
			}
		}
		
		return null;
	}
	
	/**
	 * 	Update a user by his ID with UpdateUser object which holds the new data
	 * @param userId
	 * @param newInfo
	 * @return
	 */
	public User updateUserInfo(int userId, UpdateUser newInfo){
		//Update user info
		if(ersAccess.updateUserById(userId, newInfo))
		{
			//return updated info from the database
			return getUserInfoById(userId);
		}
		else
		{
			//nothing updated
			return null;
		}
	}
	
	public boolean submitRequest(Request userRequest){
		return ersAccess.insertRequest(userRequest);
	}
	
	public LoggedInUser convertToUserDTO(User user){		
		return new LoggedInUser(user.getUSER_ID(),user.getUsername(),user.getFirstName(),user.getLastName(), user.getEmail());
	}
	
	public Request getRequest(int id){
		return ersAccess.getRequestById(id);
	}
	
	public Requests getUserRequests(int id){
		return ersAccess.getRequestsByUserId(id);
	}
	
	public Requests getPendingRequestsByStatus(int statusId){
		return ersAccess.getRequestsByStatus(statusId);
	}
	
	public Requests getResolvedRequests(){
		return ersAccess.getRequestsByResolved();
	}
	
	public List<User> getAllEmployees(){
		return ersAccess.getEmployees();
	}
	
	public EmployeesDTO convertToEmployeesDTO(){
		
		List<User> employees = getAllEmployees();
		List<LoggedInUser> employeesDTO = new ArrayList<>();
		
		for(User u: employees)
		{
			employeesDTO.add(convertToUserDTO(u));
		}
		EmployeesDTO allEmployees = new EmployeesDTO(employeesDTO);
		
		return allEmployees;
	}
	
	//convert all int id values to String for html rendering send a requestlist and status
	public RequestsDTO convertToRequestsDTO(Requests requests){
		
		ArrayList<RequestDTO> requestsDTO = new ArrayList<>();
		ERSTypes ersTypes = getTypes();
		ERSStatus ersStatus = getStatus();
		
		List<User> employees = ersAccess.getDBUsers();
		String man="",emp="";
		
		for (Request r: requests.requests){
		
			for(User e : employees)
			{
				if(r.getU_id_resolver()==e.getUSER_ID())
				{
					man = e.getFirstName()+" "+e.getLastName();
				}
				if(r.getId_author()==e.getUSER_ID())
				{
					emp = e.getFirstName()+" "+e.getLastName();
				}
			}
			requestsDTO.add(convertRequestDTO(r,ersTypes,ersStatus,emp,man));
		}
		
		RequestsDTO convertedRequestsDTO = new RequestsDTO(requestsDTO);
		
		return convertedRequestsDTO;
	}
	
	public RequestDTO convertRequestDTO(Request request,ERSTypes types, ERSStatus status,String employee,String manager){
		//call all the tables here to reference them to their String value

		
		return new RequestDTO(
				request.getId(),
				request.getAmount(),
				request.getDescription(),
				request.getReceipt(),
				request.getSubmitted(),
				request.getResolved(),				
				employee,
				manager,
				types.getTypeById(request.getRtType()),
				status.getTypeById(request.getRt_status())
				);
	}
}
