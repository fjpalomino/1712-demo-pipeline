package com.revature.dto;

import java.util.List;

public class EmployeesDTO {

	public List<LoggedInUser> employees;

	public EmployeesDTO(List<LoggedInUser> employees) {
		this.employees = employees;
	}

	public List<LoggedInUser> getEmployees() {
		return employees;
	}

	public void setEmployees(List<LoggedInUser> employees) {
		this.employees = employees;
	}
}
