package com.baeldung.reactive.controller;

import com.baeldung.reactive.model.Ping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.Stream;

@RestController
public class PingReactiveController {

    @GetMapping(value = "/ping", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Ping> getPing() {
        final Flux<Ping> pings = Flux.fromStream(Stream.generate(() -> new Ping()));
        final Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(pings, interval).map(Tuple2::getT1);
    }
}

