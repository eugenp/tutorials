package com.baeldung.architecture.hexagonal.employee.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.architecture.hexagonal.employee.domain.dto.Employee;
import com.baeldung.architecture.hexagonal.employee.domain.exceptions.EmployeeException;
import com.baeldung.architecture.hexagonal.employee.domain.repository.EmployeeRepository;

@Component
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	private int employeeId = 1;

	@Override
	public Employee findById(int employeeId) throws EmployeeException {
		if(employeeId == 0) {
			throw new EmployeeException("An employee id must be non zero value.");
		}
		return employeeRepository.findById(employeeId);
	}

	@Override
	public Employee addEmployee(String firstName, String lastName) {
		Employee employee = new Employee(firstName, lastName, employeeId++);
		return employeeRepository.addEmployee(employee);
	}
}
