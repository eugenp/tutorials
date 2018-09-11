package com.baeldung.webflux;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
public class FluxProducer {
    public Flux<String> produceFlux() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(sequence -> "Spring WebFlux - " + LocalTime.now());
    }
}
