package com.baeldung.dddhexagonalspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:anagram-application.yml" })
public class AnagramRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnagramRestApplication.class, args);
    }
}