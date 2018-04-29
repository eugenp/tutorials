package com.baeldung.reactive;

import java.time.Duration;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
public class EventStreamer {

    public Flux<Event> start() {
        return Flux.interval(Duration.ofMillis(1000))
            .map(tick -> new Event(tick));
    }
}
