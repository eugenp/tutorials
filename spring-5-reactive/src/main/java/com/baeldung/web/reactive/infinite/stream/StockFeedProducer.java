package com.baeldung.web.reactive.infinite.stream;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class StockFeedProducer {

    private static final int DELAY = 1;

    private static final int MIN_PRICE = 20;

    private static final int MAX_PRICE = 100;

    private Random random = new Random();

    @GetMapping(value = "/stocks", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Stock> generateStockFeed() {
        return Flux.fromStream(Stream.generate(() -> generatePrice(MIN_PRICE, MAX_PRICE))
            .map(price -> createStock("Test", price)))
            .log()
            .delayElements(Duration.ofSeconds(DELAY));
    }

    private Stock createStock(String name, float price) {
        Stock stock = new Stock();
        stock.setSymbol(name);
        stock.setPrice(price);
        return stock;
    }

    private float generatePrice(float min, float max) {
        float delta = max - min;
        return min + (random.nextFloat() * delta);
    }
}
