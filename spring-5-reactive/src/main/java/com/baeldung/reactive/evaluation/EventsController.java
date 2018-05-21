package com.baeldung.reactive.evaluation;

import java.time.Duration;
import java.time.Instant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class EventsController {

    @GetMapping(path = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> events() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(second -> new Event(second + 1, Instant.now()));
    }
}
