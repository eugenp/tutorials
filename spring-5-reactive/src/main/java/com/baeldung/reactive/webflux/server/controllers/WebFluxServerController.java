package com.baeldung.reactive.webflux.server.controllers;

import java.time.Duration;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.reactive.webflux.model.WebFluxEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/wfserver")
public class WebFluxServerController {
    
    @GetMapping(value = "/token", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<WebFluxEvent> getToken() {
        Flux<Long> timer = Flux.interval(Duration.ofSeconds(1));
        Flux<WebFluxEvent> events = Flux.fromStream(Stream.generate(() -> new WebFluxEvent(UUID.randomUUID().toString(), System.currentTimeMillis()))).log();
        return Flux.zip(timer, events).map(Tuple2::getT2);
    }
}