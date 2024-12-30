package com.baeldung.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "com.baeldung.controller")
@Configuration
@SpringBootApplication
public class ControllerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ControllerApplication.class, args);
    }

}