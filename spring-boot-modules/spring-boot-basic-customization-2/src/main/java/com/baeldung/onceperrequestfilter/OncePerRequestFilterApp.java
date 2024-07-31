package com.baeldung.onceperrequestfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.baeldung.onceperrequestfilter")
public class OncePerRequestFilterApp {
    public static void main(String[] args) {
        SpringApplication.run(OncePerRequestFilterApp.class, args);
    }
}