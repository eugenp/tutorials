package com.baeldung.architecture.hexagonal.employee.domain.exceptions;

public class EmployeeException extends Exception {

	public EmployeeException(String message) {
		super(message);
	}
}
