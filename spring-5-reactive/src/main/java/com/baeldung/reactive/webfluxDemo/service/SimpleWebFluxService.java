package com.baeldung.webfluxDemo.service;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class SimpleWebFluxService {

    private int generateRandomNumbers() {
        Random rand = new Random();
        int generatedNumber = rand.nextInt(10) + 1;
        return generatedNumber;
    }

    public Flux<String> getInfiniteEventStrings() {

        Flux<Integer> messageFlux = Flux.fromStream(Stream.generate(() -> generateRandomNumbers()));

        Flux<String> intervalFlux = Flux.interval(Duration.ofSeconds(1))
            .zipWith(messageFlux, (i, num) -> "Random number " + i + ": " + num + " generated at :" + new Date().toString() + "<br>");

        return intervalFlux;
    }

}
