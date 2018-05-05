package com.baeldung.webflux.server;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

@RestController
public class ServerController {

    @GetMapping("/text")
    public Flux<ServerSentEvent<String>> listText() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(sequence -> Tuples.of(sequence, ThreadLocalRandom.current()
                .nextInt()))
            .map(text -> ServerSentEvent.<String> builder()
                .data("Any random text you need - " + Long.toString(text.getT1()))
                .build());
    }
}