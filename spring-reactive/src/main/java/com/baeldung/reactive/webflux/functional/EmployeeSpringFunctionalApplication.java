package com.baeldung.reactive.webflux.functional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;

@SpringBootApplication(exclude = MongoReactiveAutoConfiguration.class)
public class EmployeeSpringFunctionalApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeSpringFunctionalApplication.class, args);
    }

}
