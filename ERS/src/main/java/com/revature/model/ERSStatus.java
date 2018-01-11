package com.revature.model;

public class ERSStatus {
	
	public final String[][] ersStatus;
	public final int size;
	
	public ERSStatus(String[][] roles, int size) {
		ersStatus = roles;
		this.size = size;
	}
	
	public String[][] showTypes(){
		return ersStatus;
	}
	
	public String getTypeById(int id)
	{
		String role ="";
		for(int i = 0; i < ersStatus.length; i++)
		{
			if(ersStatus[i][0].equals(Integer.toString(id)))
			{
				role = ersStatus[i][1];
			}
		}
		return role;
	}
}
