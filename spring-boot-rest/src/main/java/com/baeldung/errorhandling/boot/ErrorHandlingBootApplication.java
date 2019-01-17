package com.baeldung.errorhandling.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("errorhandling-application.properties")
public class ErrorHandlingBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErrorHandlingBootApplication.class, args);
    }

}
