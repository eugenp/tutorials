package com.baeldung.dynamicvalidation;

import javax.annotation.security.RolesAllowed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DynamicValidationApp {
    @RolesAllowed("*")
    public static void main(String[] args) {
        SpringApplication.run(DynamicValidationApp.class, args);
    }
}
