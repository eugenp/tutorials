package com.baeldung.contexts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.baeldung.controller.circularviewpath")
public class CircularViewPathApp {

    public static void main(String[] args) {
        SpringApplication.run(CircularViewPathApp.class, args);
    }
}
