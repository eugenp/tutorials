package com.baeldung.reactive.webflux;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class EventStreamController {

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> getEvents() {
        return Flux.interval(Duration.ofMillis(1000))
            .map(tick -> new Event(tick, "This is Event #" + tick));
    }

}
