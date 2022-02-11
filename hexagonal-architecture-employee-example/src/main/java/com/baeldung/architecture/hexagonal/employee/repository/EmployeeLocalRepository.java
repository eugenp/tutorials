/**
 * 
 */
package com.baeldung.architecture.hexagonal.employee.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baeldung.architecture.hexagonal.employee.dto.Employee;

/**
 * Local Repository, Secondary/Driven Adapter.
 */
@Component
public class EmployeeLocalRepository implements EmployeeRepository {

	private Map<Integer, Employee> employeeRepository = new HashMap<Integer, Employee>();
	private int employeeId = 1;

	public Employee addEmployee(String firstName, String lastName) {
		Employee employee = new Employee(firstName, lastName, employeeId++);
		return employeeRepository.put(employee.getEmployeeId(), employee);
	}

	public Employee findById(int employeeId) {
		return employeeRepository.get(employeeId);
	}

}
