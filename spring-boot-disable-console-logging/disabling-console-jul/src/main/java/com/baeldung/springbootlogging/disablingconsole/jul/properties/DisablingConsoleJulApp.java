package com.baeldung.springbootlogging.disablingconsole.jul.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DisablingConsoleJulApp {
    public static void main(String[] args) {
        SpringApplication.run(DisablingConsoleJulApp.class, args);
    }
}
