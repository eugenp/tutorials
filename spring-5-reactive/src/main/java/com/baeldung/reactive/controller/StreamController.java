package com.baeldung.reactive.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

/**
 * Controller that produces infinite stream of integer numbers 
 *
 */
@RestController
public class StreamController {
    /**
     * Produce an infinite stream of integers (Long). 
     * 
     * The stream starts with zero, once a second publish another integer 
     * incremented by one.
     * 
     * @return a stream of Long
     */
    @GetMapping(path = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> getSequence() {
        return Flux.interval(Duration.ofSeconds(1));
    }
}
