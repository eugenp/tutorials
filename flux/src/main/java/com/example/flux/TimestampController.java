package com.example.flux;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class TimestampController {

    @GetMapping("/timestamp")
    public Flux<String> getTimestampFlux() {
        Flux<String> timestamps = Flux.fromStream(Stream.iterate(timestamp(null), this::timestamp));
        return timestamps.delayElements(Duration.ofSeconds(5));
    }

    private String timestamp(String previous) {
        return Long.toString(System.currentTimeMillis());
    }
}
