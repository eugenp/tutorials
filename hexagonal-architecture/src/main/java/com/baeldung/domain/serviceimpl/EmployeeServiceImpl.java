package com.baeldung.domain.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.domain.Employee;
import com.baeldung.inboundports.EmployeeService;
import com.baeldung.outboundports.EmployeeRepository;

/**
 * This is the use case for business logic implementation. The use cases and business domain objects are inside of the hexagonal.
 *
 **/
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void createEmployeeInfo(Employee emp) {
		employeeRepository.createEmployeeInfo(emp);

	}

	@Override
	public Employee getEmployeeInfo(Integer empId) {

		return employeeRepository.getEmployeeInfo(empId);
	}

	@Override
	public List<Employee> getAllEmployeesInfo() {
		
		return employeeRepository.getAllEmployeesInfo();
	}

}
