package com.baeldung.reactive.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.time.Duration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController("/foos")
public class FooReactiveController {

    @GetMapping("/{id}")
    public Mono<Foo> getFoo(@PathVariable("id") long id) {
        return Mono.just(new Foo(id, randomAlphabetic(6)));
    }

    @GetMapping("/")
    public Flux<Foo> getAllFoos() {
        final ConnectableFlux<Foo> flux = Flux.<Foo> create(fluxSink -> {
            while (true) {
                fluxSink.next(new Foo(System.currentTimeMillis(), randomAlphabetic(6)));
            }
        }).sample(Duration.ofSeconds(1)).publish();

        return flux;
    }

}
