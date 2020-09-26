package com.baeldung.architecture.hexagonal.port.inbound;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baeldung.architecture.hexagonal.domain.Employee;

@Component
public interface HandleUserInteraction {
	
	public List<Employee> showAllEmployee();
	
	public Employee getEmployeeById(int id);
	
	public void addNewEmployee(Employee employee);

}
