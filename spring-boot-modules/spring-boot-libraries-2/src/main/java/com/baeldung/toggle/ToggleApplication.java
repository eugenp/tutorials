package com.baeldung.toggle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.security.RolesAllowed;

@SpringBootApplication
public class ToggleApplication {
    @RolesAllowed("*")
    public static void main(String[] args) {
        SpringApplication.run(ToggleApplication.class, args);
    }
}
