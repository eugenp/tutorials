package com.baeldung.reactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class ReactiveController {
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/infinite-event-stream")
    public Flux<Long> getEventStream() {
        return Flux.interval(Duration.ofSeconds(1));
    }
}
