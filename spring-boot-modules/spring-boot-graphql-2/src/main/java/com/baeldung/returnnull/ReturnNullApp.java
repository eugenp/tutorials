package com.baeldung.returnnull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReturnNullApp {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.default", "returnnull");
        SpringApplication.run(ReturnNullApp.class, args);
    }
}

