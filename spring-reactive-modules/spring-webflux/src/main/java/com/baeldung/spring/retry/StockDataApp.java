package com.baeldung.spring.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class StockDataApp {
    public static void main(String[] args) {
        SpringApplication.run(StockDataApp.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
