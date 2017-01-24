package com.baeldung.di.constructor;

import com.baeldung.di.employee.Employee;
import com.baeldung.di.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author shaffenbredl
 *
 */

public class ConstructorInjection {

	EmployeeRepository employeeRepository;

	@Autowired
	public ConstructorInjection(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public Employee getEmployee(long empId) {
		Employee employee = employeeRepository.getEmployeeById(empId);
		return employee;
	}

}
