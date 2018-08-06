package com.baeldung.reactive.server;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class WebFluxServer {

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Date> events() {
        Flux<Date> eventFlux = Flux.fromStream(Stream.generate(() -> new Date()));
        Flux<Long> fluxInterval = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(eventFlux, fluxInterval).map(Tuple2::getT1);
    }
}
