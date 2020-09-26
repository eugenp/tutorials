package com.baeldung.architecture.hexagonal.port.outbound;

import java.util.List;

import com.baeldung.architecture.hexagonal.domain.Employee;

public interface EmployeeRepository {
	
	public List<Employee> fetchAllEmployees();
	
	public Employee fetchEmployee(int id);
	
	public void insertEmployee(Employee employee);
	
}
