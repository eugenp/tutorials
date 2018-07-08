package com.baeldung.reactive.controller;

import com.baeldung.reactive.model.Foo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@RestController
public class FooReactiveController {

    @GetMapping("/foos/{id}")
    public Mono<Foo> getFoo(@PathVariable("id") long id) {
        return Mono.just(new Foo(id, randomAlphabetic(6)));
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/foos")
    public Flux getAllFoos() {
        return Flux.create(fluxSink -> {
            while (!fluxSink.isCancelled()) {
                fluxSink.next(new Foo(new Random().nextLong(), randomAlphabetic(6)));
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).share();
    }
}
