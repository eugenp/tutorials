package com.baeldung.reactive.controller;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class TemperatureController {

    @GetMapping(value = "/temperatures", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getTemperature() {
        Random r = new Random();
        int low = 0;
        int high = 50;
        return Flux.fromStream(Stream.generate(() -> r.nextInt(high - low) + low))
            .delayElements(Duration.ofSeconds(1));

    }
}
