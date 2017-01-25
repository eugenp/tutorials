package com.baeldung.di.field;

import com.baeldung.di.employee.Employee;
import com.baeldung.di.repository.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author shaffenbredl
 *
 */

public class FieldInjection {

	@Autowired
	EmployeeRepositoryImpl employeeRepositoryImpl;

	public Employee getEmployee(long empId) {
		Employee employee = employeeRepositoryImpl.getEmployeeById(empId);
		return employee;
	}

}
