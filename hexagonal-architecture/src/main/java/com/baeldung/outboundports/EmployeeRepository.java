package com.baeldung.outboundports;

import java.util.List;

import com.baeldung.domain.Employee;

public interface EmployeeRepository {

	void createEmployeeInfo(Employee emp);

	Employee getEmployeeInfo(Integer empId);

	List<Employee> getAllEmployeesInfo();
}
