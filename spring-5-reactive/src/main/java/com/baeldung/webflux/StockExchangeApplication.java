package com.baeldung.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class StockExchangeApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockExchangeApplication.class, args);
    }
}
