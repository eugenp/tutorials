package com.baeldung.controller;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.model.Event;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@SpringBootApplication
@RestController
public class ReactiveServer {

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<Event> events() {
        Flux<Event> eventFlux = Flux.fromStream(Stream.generate(() -> new Event(System.currentTimeMillis(), new Date())));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(eventFlux, durationFlux)
            .map(Tuple2::getT1);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactiveServer.class).properties(Collections.singletonMap("server.port", "8081"))
            .run(args);
    }
}
