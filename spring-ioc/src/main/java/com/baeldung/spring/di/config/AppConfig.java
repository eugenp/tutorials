package com.baeldung.spring.di.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.di.service.Department;
import com.baeldung.spring.di.service.Employee;
import com.baeldung.spring.di.serviceImpl.DepartmentImp;
import com.baeldung.spring.di.serviceImpl.EmployeeImp;

@Configuration
@ComponentScan(basePackages = "com.baeldung.spring.di")
public class AppConfig {

	@Bean
	public Employee getEmployee() {
		return new EmployeeImp();
	}

	@Bean
	public Department getDepartment() {
		return new DepartmentImp();
	}
}
