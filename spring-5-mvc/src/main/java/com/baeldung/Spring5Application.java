package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication( exclude = SecurityAutoConfiguration.class)
public class Spring5Application {

    public static void main(String[] args) {
        SpringApplication.run(Spring5Application.class, args);
    }
}
