package com.baeldung.pattern.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class EmployeeApp {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApp.class, args);
    }

}
