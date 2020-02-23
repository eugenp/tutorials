package com.baeldung.internationalization;

import javax.annotation.security.RolesAllowed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InternationalizationApp {
    @RolesAllowed("*")
    public static void main(String[] args) {
        SpringApplication.run(InternationalizationApp.class, args);
    }
}
