package com.baeldung.controller;

import com.baeldung.model.Event;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.Stream;

@RestController
public class EventController {

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> streamEvents() {
        long eventId = System.currentTimeMillis();
        Flux<Event> eventFlux = Flux
                .fromStream(Stream.generate(() -> new Event(eventId)));
        Flux<Long> interval = Flux
                .interval(Duration.ofSeconds(1));
        return Flux
                .zip(eventFlux, interval)
                .map(Tuple2::getT1);
    }
}
