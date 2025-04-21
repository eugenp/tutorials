package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Spring Boot class that scans only the repository package to avoid
 * bean conflicts with shared codebase's manual MongoDB configuration.
 * This ensures that MongoDataAutoConfiguration handles MongoDB setup while preventing
 * duplicate bean definitions from external config classes.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.repository")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}