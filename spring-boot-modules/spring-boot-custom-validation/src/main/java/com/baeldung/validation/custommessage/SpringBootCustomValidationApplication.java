package com.baeldung.validation.custommessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCustomValidationApplication {
    public static void main(String[] args) {
        LogbackPing.ping(); // Ensures logback-core is included
        SpringApplication.run(SpringBootCustomValidationApplication.class, args);
    }
}