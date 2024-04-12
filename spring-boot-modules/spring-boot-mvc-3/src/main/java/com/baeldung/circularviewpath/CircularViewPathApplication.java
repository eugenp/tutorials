package com.baeldung.circularviewpath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot launcher for an application
 *
 */
@SpringBootApplication(scanBasePackages = "com.baeldung.controller.circularviewpath")
public class CircularViewPathApplication {

    /**
     * Launches a Spring Boot application
     *
     * @param args null
     */
    public static void main(String[] args) {
        SpringApplication.run(CircularViewPathApplication.class, args);
    }
}
