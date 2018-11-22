package com.baeldung.hexagonal.domain;

public class EmployeeService {

	private Input input;
	private EmployeeRepository employeeRepository;

	public EmployeeService(Input input, EmployeeRepository employeeRepository) {
		this.input = input;
		this.employeeRepository = employeeRepository;
	}

	public Employee registerEmployee() {
		Employee employee = input.createEmployee();
		employee = employeeRepository.saveEmployee(employee);
		return employee;
	}

}
