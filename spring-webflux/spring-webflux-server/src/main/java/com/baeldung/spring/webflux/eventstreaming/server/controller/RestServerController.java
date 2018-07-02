package com.baeldung.spring.webflux.eventstreaming.server.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.webflux.eventstreaming.model.Event;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
public class RestServerController {

    @RequestMapping(name = "events/{id}", method = RequestMethod.GET)
    public Mono<Event> getEventById(@PathVariable long id) {
        return Mono.just(new Event(id, "new event", LocalDateTime.now()));
    }

    static int id = 0;

    @GetMapping(value = "events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> getAllEvents() {
        id = 0;
        Flux<Event> eventFlux = Flux.fromStream(Stream.generate(() -> {
            id++;
            return new Event(id, "Event " + id, LocalDateTime.now());
        }));

        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1L));

        return Flux.zip(eventFlux, durationFlux)
            .map(Tuple2::getT1);
    }
}
