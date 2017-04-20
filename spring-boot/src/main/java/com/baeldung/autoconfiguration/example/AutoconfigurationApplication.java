package com.baeldung.autoconfiguration.example;

import javax.annotation.security.RolesAllowed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoconfigurationApplication {
    @RolesAllowed("*")
    public static void main(String[] args) {
        System.setProperty("security.basic.enabled", "false");
        SpringApplication.run(AutoconfigurationApplication.class, args);
    }
}
