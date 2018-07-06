package com.baeldung.reactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class FibonacciController {
    @GetMapping(path = "/fibonacci", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux fibonacciSequence() {
        return Flux.fromStream(fibonacciStream())
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    private Stream<Long> fibonacciStream() {
        return Stream.iterate(new Long[]{0L, 1L}, prev -> new Long[]{prev[1], prev[0] + prev[1]})
                .map(pair -> pair[1]);
    }
}
