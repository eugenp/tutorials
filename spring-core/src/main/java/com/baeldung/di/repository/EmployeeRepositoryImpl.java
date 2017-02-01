package com.baeldung.di.repository;

import com.baeldung.di.employee.Employee;
import org.springframework.stereotype.Component;

/**
 * 
 * @author shaffenbredl
 *
 */

@Component
public class EmployeeRepositoryImpl implements EmployeeRepository {

	@Override
	public Employee getEmployeeById(long empId) {
		return new Employee(empId);
	}

}
