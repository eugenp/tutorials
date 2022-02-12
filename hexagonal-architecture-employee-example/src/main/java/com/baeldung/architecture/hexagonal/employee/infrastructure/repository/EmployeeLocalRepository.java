package com.baeldung.architecture.hexagonal.employee.infrastructure.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baeldung.architecture.hexagonal.employee.domain.dto.Employee;
import com.baeldung.architecture.hexagonal.employee.domain.repository.EmployeeRepository;

@Component
public class EmployeeLocalRepository implements EmployeeRepository {
	private Map<Integer, Employee> employeeRepository = new HashMap<Integer, Employee>();

	public Employee addEmployee(Employee employee) {
		int employeeId = employee.getEmployeeId();
		return employeeRepository.put(employeeId, employee);
	}

	public Employee findById(int employeeId) {
		return employeeRepository.get(employeeId);
	}
}
