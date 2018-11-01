package com.baeldung.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.model.Employee;

public class EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	private EmployeeRepository repo;

	public EmployeeService(EmployeeRepository repo) {
		this.repo = repo;
	}

	public Employee saveEmployee(Employee emp) {
		logger.info("Inside EmployeeService.saveEmployee()");
		return repo.saveEmployee(emp);
	}
}