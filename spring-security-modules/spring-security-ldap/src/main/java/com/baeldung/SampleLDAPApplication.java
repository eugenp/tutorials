package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Main Application Class - uses Spring Boot. Just run this as a normal Java
 * class to run up a Jetty Server (on http://localhost:8080)
 *
 */
@SpringBootApplication
public class SampleLDAPApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SampleLDAPApplication.class, args);
    }

}