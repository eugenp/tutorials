package com.baeldung.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeSpringApplication {

    public static void main(String[] args) {
        
        SpringApplication.run(EmployeeSpringApplication.class, args);

        EmployeeWebClient employeeWebClient = new EmployeeWebClient();
        employeeWebClient.consume();

        EmployeeOfTheSecondWebClient employeeOfTheSecondWebClient = new EmployeeOfTheSecondWebClient();
        employeeOfTheSecondWebClient.consume();

    }

}
