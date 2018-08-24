package com.baeldung.web.reactive.infinite.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

@Component
public class StockFeedConsumer {

    private static final Logger logger = LoggerFactory.getLogger(StockFeedConsumer.class);

    private WebClient webClient;

    @PostConstruct
    public void initialize() {
        webClient = WebClient.create("http://localhost:8080");
    }

    public void readFeed() {

        webClient.get()
            .uri("/stocks")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(Stock.class)
            .subscribe(stock -> {
                logger.info(stock.toString());
            });
    }
}