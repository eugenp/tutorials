package com.baeldung.reactive.events;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class StocksController {

    @GetMapping(value = "/stockprices")
    public Flux<Integer> getStockPrices() {
        Random random = new Random();

        return Flux.fromStream(Stream.generate(() -> random.nextInt(1000)))
            .delayElements(Duration.ofSeconds(1));
    }
}