package com.baeldung.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HexagonalApplication {

    public static void main(String[] args) {
        System.setProperty("management.security.enabled", "false");
        SpringApplication.run(HexagonalApplication.class, args);
    }
}
