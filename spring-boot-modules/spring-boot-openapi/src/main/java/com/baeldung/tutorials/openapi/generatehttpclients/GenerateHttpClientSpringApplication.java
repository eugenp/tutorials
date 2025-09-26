package com.baeldung.tutorials.openapi.generatehttpclients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.tutorials.openapi.quotes.QuotesApplication;

@SpringBootApplication
public class GenerateHttpClientSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotesApplication.class, args);

    }
}
