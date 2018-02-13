package com.baeldung.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.spring.config.ApplicationConfig;
import com.baeldung.spring.service.EmployeeService;

public class SpringSetterDIAnnotationClient {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		EmployeeService employeeService = context.getBean(EmployeeService.class);
		employeeService.printEmployeeMessage();
		context.close();
	}
}
