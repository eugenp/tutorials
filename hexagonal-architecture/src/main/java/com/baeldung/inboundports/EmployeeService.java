package com.baeldung.inboundports;

import java.util.List;

import com.baeldung.domain.Employee;

public interface EmployeeService {

	void createEmployeeInfo(Employee emp);

	Employee getEmployeeInfo(Integer empId);

	List<Employee> getAllEmployeesInfo();
}
