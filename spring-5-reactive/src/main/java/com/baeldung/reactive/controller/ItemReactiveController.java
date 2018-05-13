package com.baeldung.reactive.controller;

import com.baeldung.reactive.model.Item;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@RestController
public class ItemReactiveController {
    AtomicInteger atomicInteger = new AtomicInteger(0);

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/items")
    public Flux<Item> getAllItems() {
        final Flux<Item> itemFlux = Flux.fromStream(Stream.generate(
                () -> new Item(String.valueOf(atomicInteger.addAndGet(1)))));
        final Flux<Long> emitFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(itemFlux, emitFlux).map(Tuple2::getT1);
    }
}
