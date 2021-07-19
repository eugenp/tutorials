package com.baeldung.activitiwithspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ActivitiWithSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActivitiWithSpringApplication.class, args);
    }
}
