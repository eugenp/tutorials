package com.baeldung.spring.kafka.trusted.packages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrustedPackageApp {

    public static void main(String[] args) {
        SpringApplication.run(TrustedPackageApp.class, args);
    }
}