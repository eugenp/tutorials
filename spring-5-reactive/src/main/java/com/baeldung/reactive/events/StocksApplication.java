package com.baeldung.reactive.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = { StocksController.class })
public class StocksApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksApplication.class, args);

        StocksWebClient webClient = new StocksWebClient();
        webClient.getStockPrices();
    }
}
