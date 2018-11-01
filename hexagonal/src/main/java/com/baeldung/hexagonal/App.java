package com.baeldung.hexagonal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.domain.EmployeeService;
import com.baeldung.model.Employee;
import com.baeldung.repo.DatabaseEmployeeRepository;
import com.baeldung.repo.FileSystemEmployeeRepository;

public class App {
	
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		Employee employee1 = new Employee();
        employee1.setName("Eric F");
        employee1.setAddress("Address 1, New York");
        EmployeeService employeeService1 = new EmployeeService(FileSystemEmployeeRepository.getInstance());
        employeeService1.saveEmployee(employee1);
        logger.debug("Employee object saved to file successfully");
        
        Employee employee2 = new Employee();
        employee2.setName("John Matt2");
        employee2.setAddress("Address 2, New York");
        EmployeeService employeeService2 = new EmployeeService(DatabaseEmployeeRepository.getInstance());
        employeeService2.saveEmployee(employee2);
        logger.debug("Employee object saved to Database successfully");
	}

}
