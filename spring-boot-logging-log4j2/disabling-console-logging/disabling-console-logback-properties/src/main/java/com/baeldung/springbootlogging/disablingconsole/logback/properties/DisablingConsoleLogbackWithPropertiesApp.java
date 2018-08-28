package com.baeldung.springbootlogging.disablingconsole.logback.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DisablingConsoleLogbackWithPropertiesApp {
    public static void main(String[] args) {
        SpringApplication.run(DisablingConsoleLogbackWithPropertiesApp.class, args);
    }
}
