package com.baeldung.springretry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSchedulingApplication {

    public static void main(String[] args) {
        // This starts the Spring Boot application, which will load AppConfig.java
        // via component scanning.
        SpringApplication.run(SpringSchedulingApplication.class, args);
    }
}
