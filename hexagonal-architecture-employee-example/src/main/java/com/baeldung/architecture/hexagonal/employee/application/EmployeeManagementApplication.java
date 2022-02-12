package com.baeldung.architecture.hexagonal.employee.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.baeldung.architecture.hexagonal.employee")
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		try {
            SpringApplication.run(EmployeeManagementApplication.class, args);
        } catch (Exception e) {
        }
	}

}
