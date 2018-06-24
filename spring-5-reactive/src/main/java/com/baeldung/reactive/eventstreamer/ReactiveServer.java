package com.baeldung.reactive.eventstreamer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by Nakul on 23-06-2018.
 */
@SpringBootApplication
@RestController
public class ReactiveServer {

    @GetMapping("/events/{id}")
    Mono<Message> eventById(@PathVariable long id) {
        return Mono.just(new Message(id, new Date()));
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/events")
    Flux<Message> events() {

        Flux<Message> eventFlux = Flux.fromStream(Stream.generate(() -> new Message(System.currentTimeMillis(), new Date())));

        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));

        return Flux.zip(eventFlux, durationFlux)
            .map(Tuple2::getT1);

    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveServer.class, args);
    }
}
