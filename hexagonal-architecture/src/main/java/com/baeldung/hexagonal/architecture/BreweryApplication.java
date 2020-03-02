package com.baeldung.hexagonal.architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.baeldung.hexagonal.architecture")
public class BreweryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreweryApplication.class, args);
    }
}
