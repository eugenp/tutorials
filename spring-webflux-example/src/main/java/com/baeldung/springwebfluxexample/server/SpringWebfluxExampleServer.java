package com.baeldung.springwebfluxexample.server;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.springwebfluxexample.model.Event;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@SpringBootApplication
@RestController
public class SpringWebfluxExampleServer {
    @GetMapping("/serverevents/{id}")
    public Mono<Event> serverEventsById(@PathVariable long id) {
        return Mono.just(new Event(id, new Date()));
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/serverevents")
    public Flux<Event> streamOfEvents() {
        Flux<Event> eventsOfFlux = Flux.fromStream(Stream.generate(() -> new Event(System.currentTimeMillis(), new Date())));

        Flux<Long> duration = Flux.interval(Duration.ofSeconds(1));

        return Flux.zip(eventsOfFlux, duration)
            .map(Tuple2::getT1);

    }

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxExampleServer.class, args);
    }
}
