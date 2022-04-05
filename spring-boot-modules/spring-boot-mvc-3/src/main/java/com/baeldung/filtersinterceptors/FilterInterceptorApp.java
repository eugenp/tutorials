package com.baeldung.filtersinterceptors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.baeldung.filtersinterceptors")
public class FilterInterceptorApp {
    public static void main(String[] args) {
        SpringApplication.run(FilterInterceptorApp.class, args);
    }
}