package com.baeldung.reactive.controller;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.reactive.model.Event;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class SimpleReactiveController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/events")
    public Flux<Event> getEvents() {
        final Flux<Event> eventsFlux = Flux.fromStream(Stream.generate(() -> new Event(new Random().nextInt(), generateRandomEventName())));
        return Flux.zip(eventsFlux, Flux.interval(Duration.ofSeconds(1)))
            .map(Tuple2::getT1);
    }

    private String generateRandomEventName() {
        final String[] events = "Saved,Deleted,Updated".split(",");
        return events[new Random().nextInt(events.length)];
    }

}
