package com.baeldung.reactive.controller;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.reactive.model.Stock;

import reactor.core.publisher.Flux;

@RestController
public class StockBrokerController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/getStockPrice")
    public Flux<Object> getStockUpdates() {
        final Flux<Stock> stockFlux = Flux.fromStream(Stream.generate(() -> new Stock(new Random().nextFloat(), "WebFluxStock", new Date())));
        final Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(stockFlux, intervalFlux)
            .map(t1 -> t1.getT1());
    }
}
