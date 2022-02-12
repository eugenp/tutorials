package com.baeldung.architecture.hexagonal.employee.domain.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Employee implements Serializable{
	private String firstName;
	private String lastName;
	private int employeeId;

	public Employee() {
	}
	
	public Employee(String firstName, String lastName, int employeeId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeId = employeeId;
	}
}
