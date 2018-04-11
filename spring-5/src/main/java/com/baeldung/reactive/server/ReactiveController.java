package com.baeldung.reactive.server;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
@RestController
public class ReactiveController {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveController.class, args);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/integers")
    Flux<Integer> integers() {
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        Flux<Integer> integersFlux = Flux.fromStream(Stream.generate(() -> new Random().nextInt(100)));
        return Flux.zip(integersFlux, durationFlux)
            .map((t) -> t.getT1());
    }
}
