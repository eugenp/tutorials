package com.emp.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="com.emp.demo")
public class BeanConfig {

	@Autowired 
	IEmployeeRole employeeRole;
	
	
	public String getEmployeeRole(){
		return employeeRole.getEmployeeRole();
	}
	
	@Bean
	public Employee getEmployee(){
		Employee employee = new Employee();
		employee.setId(2L);
		employee.setName("Vigeta");
		return employee;
	}
	

	
	
}
