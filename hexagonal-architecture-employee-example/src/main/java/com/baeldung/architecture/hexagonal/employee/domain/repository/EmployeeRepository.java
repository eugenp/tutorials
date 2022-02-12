package com.baeldung.architecture.hexagonal.employee.domain.repository;

import com.baeldung.architecture.hexagonal.employee.domain.dto.Employee;

public interface EmployeeRepository {
	public Employee findById(int employeeId);
	public Employee addEmployee(Employee employee);
}
