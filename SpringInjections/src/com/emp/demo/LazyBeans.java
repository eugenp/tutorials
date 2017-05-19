package com.emp.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class LazyBeans implements IEmployeeRole {

	@Override
	public String getEmployeeRole() {
		
		return "WebMaster";
	}


	@Bean("construct")
	public Employee getEmployee(){
		return new Employee(1L, "Baeldung");
	}
	
}
