package com.baeldung.webflux.reactiveapp;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

@Component
public class EventHandler {

    public Mono<ServerResponse> getEvents(ServerRequest serverRequest) {
        Flux<Event> eventFlux = Flux.fromStream(
                Stream.generate(
                        () -> new Event(new Random().nextInt(10), System.currentTimeMillis())));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        Flux<Event> response = Flux
                .zip(eventFlux, durationFlux)
                .map(Tuple2::getT1);
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(response, Event.class);
    }
}
