package com.baeldung.domain;

import com.baeldung.model.Employee;

public interface EmployeeRepository {

	public Employee saveEmployee(Employee emp);
}
