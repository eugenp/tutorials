package com.baeldung.toggle;

import javax.annotation.security.RolesAllowed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.autoconfiguration.MySQLAutoconfiguration;

@SpringBootApplication(exclude = MySQLAutoconfiguration.class)
public class ToggleApplication {
    @RolesAllowed("*")
    public static void main(String[] args) {
        System.setProperty("security.basic.enabled", "false");
        SpringApplication.run(ToggleApplication.class, args);
    }
}
