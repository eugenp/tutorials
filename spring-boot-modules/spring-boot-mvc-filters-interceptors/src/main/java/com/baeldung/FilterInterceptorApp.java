package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class FilterInterceptorApp {
    public static void main(String[] args) {
        SpringApplication.run(FilterInterceptorApp.class, args);
    }
}