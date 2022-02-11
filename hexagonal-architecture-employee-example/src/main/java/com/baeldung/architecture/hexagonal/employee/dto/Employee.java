/**
 * 
 */
package com.baeldung.architecture.hexagonal.employee.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Employee, A Domain Object.
 */
@ToString
@Getter
@Setter
public class Employee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9000990038117460470L;
	private String firstName;
	private String lastName;
	private int employeeId;

	public Employee(String firstName, String lastName, int employeeId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeId = employeeId;
	}
}
