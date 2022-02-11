/**
 * 
 */
package com.baeldung.architecture.hexagonal.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class EmployeeManagementApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
            SpringApplication.run(EmployeeManagementApplication.class, args);
            log.info("Employee Management application initialized.");
        } catch (Exception e) {
            log.error("Employee Management application failed to start.");
        }
	}

}
