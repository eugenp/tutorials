package com.baeldung.exceptiontesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Application Class - uses Spring Boot. Just run this as a normal Java
 * class to run up a Jetty Server (on http://localhost:8082/spring-rest-full)
 *
 */
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("com.baeldung.exceptiontesting")
@SpringBootApplication
public class ExceptionTestingApplication extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(ExceptionTestingApplication.class, args);
    }

}