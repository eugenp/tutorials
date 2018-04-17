package com.baeldung.reactive.controller;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.event.Event;

@RestController
public class WebfluxController {

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event<String>> subscribe() {

        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        Flux<String> dataFlux = Flux.fromStream(Stream.generate(() -> "Event "));

        return Flux.zip(interval, dataFlux, (countItem, messageItem) -> new Event<String>(messageItem.concat(Long.toString(countItem + 1))));

    }
}
