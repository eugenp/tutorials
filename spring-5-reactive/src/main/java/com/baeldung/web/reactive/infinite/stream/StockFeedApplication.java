package com.baeldung.web.reactive.infinite.stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockFeedApplication {

    @Bean
    CommandLineRunner commandLineRunner(StockFeedConsumer stockFeedConsumer) {
        return args -> stockFeedConsumer.readFeed();
    }

    public static void main(String[] args) {
        SpringApplication.run(StockFeedApplication.class, args);
    }
}