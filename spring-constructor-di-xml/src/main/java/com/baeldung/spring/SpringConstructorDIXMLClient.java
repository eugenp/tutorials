package com.baeldung.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.spring.service.EmployeeService;

public class SpringConstructorDIXMLClient {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		EmployeeService employeeService = context.getBean(EmployeeService.class);
		employeeService.printEmployeeMessage();
		context.close();
	}
}
