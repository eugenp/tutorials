package com.baeldung.errorhandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("errorhandling")
@SpringBootApplication(scanBasePackages = "com.baeldung.errorhandling")
public class ErrorHandlingApplication {

    public static void main(String [] args) {
        System.setProperty("spring.profiles.active", "errorhandling");
        SpringApplication.run(ErrorHandlingApplication.class, args);
    }
}
