package com.revature.model;

public class ERSTypes {
	
	public final String[][] ersTypes;
	public final int size;
	
	public ERSTypes(String[][] roles, int size) {
		ersTypes = roles;
		this.size = size;
	}
	
	public String[][] showTypes(){
		return ersTypes;
	}
	
	public String getTypeById(int id)
	{
		String role ="";
		for(int i = 0; i < ersTypes.length; i++)
		{
			if(ersTypes[i][0].equals(Integer.toString(id)))
			{
				role = ersTypes[i][1];
			}
		}
		return role;
	}
}
