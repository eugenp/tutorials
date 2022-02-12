package com.baeldung.architecture.hexagonal.employee.domain.service;

import com.baeldung.architecture.hexagonal.employee.domain.dto.Employee;
import com.baeldung.architecture.hexagonal.employee.domain.exceptions.EmployeeException;

public interface EmployeeService {
	Employee findById(int employeeId) throws EmployeeException;
	Employee addEmployee(String firstName, String lastName);
}
