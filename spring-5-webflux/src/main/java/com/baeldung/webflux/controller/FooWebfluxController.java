package com.baeldung.webflux.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.webflux.model.Foo;
import com.baeldung.webflux.service.FooWebClientService;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class FooWebfluxController {

    @Autowired
    FooWebClientService fooWebClientService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/foos")
    public Flux<Foo> getFooStream() {
        final Flux<Foo> foosFlux = Flux.fromStream(Stream.generate(() -> 
                                                     new Foo(new Random().nextLong(), randomAlphabetic(8))));
        final Flux<Long> emmitFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(foosFlux, emmitFlux).map(Tuple2::getT1);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/foos2")
    public Flux<ServerSentEvent<Foo>> getFooStream2() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(foo -> ServerSentEvent.<Foo> builder()
                        .data(new Foo(new Random().nextLong(), randomAlphabetic(8)))
                        .build());
    }

    @GetMapping("/client/foos")
    public void consumeFooStream() {
        fooWebClientService.initFooWebClient();
    }

}
