package com.baeldung.reactive.webflux.server.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;

@RestController
public class PriceController {
    @GetMapping(value = "/api/stock/currentPrice",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Double> getCurrentPriceByStock(@RequestParam String stockName) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(x -> getPrice(stockName));
    }

    Random random = new Random();

    public double getPrice(String stockName) {
        //Instead of returning sample data as below, return real-time data by fetching it from database for a given stockName
        double lowerLimit = 10;
        double upperLimit = 11;
        return (lowerLimit + random.nextDouble() * (upperLimit - lowerLimit));
    }
}

