package com.baeldung.tutorials.openapi.quotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.tutorials.openapi.quotes.service.BrokerService;

@SpringBootApplication
public class QuotesApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotesApplication.class, args);
    }
}
