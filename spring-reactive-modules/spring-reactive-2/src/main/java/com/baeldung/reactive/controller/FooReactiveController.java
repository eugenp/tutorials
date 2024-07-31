package com.baeldung.reactive.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.reactive.model.Foo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
public class FooReactiveController {

    @GetMapping("/foos/{id}")
    public Mono<Foo> getFoo(@PathVariable("id") long id) {
        return Mono.just(new Foo(id, randomAlphabetic(6)));
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/foos")
    public Flux<Foo> getAllFoos2() {
        final Flux<Foo> foosFlux = Flux.fromStream(Stream.generate(() -> new Foo(new Random().nextLong(), randomAlphabetic(6))));
        final Flux<Long> emmitFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(foosFlux, emmitFlux).map(Tuple2::getT1);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/foos2")
    public Flux<Foo> getAllFoos() {
        final Flux<Foo> flux = Flux.<Foo> create(fluxSink -> {
            while (true) {
                fluxSink.next(new Foo(new Random().nextLong(), randomAlphabetic(6)));
            }
        }).sample(Duration.ofSeconds(1)).log();

        return flux;
    }

}
