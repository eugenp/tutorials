package com.baeldung.springamqp.exponentialbackoff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExponentialBackoffApp {
    public static void main(String[] args) {
        SpringApplication.run(ExponentialBackoffApp.class, args);
    }
}
