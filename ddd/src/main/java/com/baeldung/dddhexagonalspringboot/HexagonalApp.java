package com.baeldung.dddhexagonalspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HexagonalApp {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "dddhexagonalspringboot");
        SpringApplication.run(HexagonalApp.class, args);
    }

}
