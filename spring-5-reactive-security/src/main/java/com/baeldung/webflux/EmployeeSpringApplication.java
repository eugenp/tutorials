package com.baeldung.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeSpringApplication {

    public static void main(String[] args) {
        
        SpringApplication.run(EmployeeSpringApplication.class, args);

        EmployeeWebClient employeeWebClient = new EmployeeWebClient();
        employeeWebClient.consume();

        // Let the main thread sleep for 5 seconds
        // in order to make sure that the employees list has been displayed completely.
        try {
            Thread.sleep(5000);
        } catch(InterruptedException e) {
            System.out.println(e);
        }

        EmployeeOfTheSecondWebClient employeeOfTheSecondWebClient = new EmployeeOfTheSecondWebClient();
        employeeOfTheSecondWebClient.consume();

    }

}
