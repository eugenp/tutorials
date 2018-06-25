package com.baeldung.reactive;

import com.baeldung.reactive.model.SimpleEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
public class ReactiveEmitter {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveEmitter.class, args);
    }

    @GetMapping(value = "/event/{id}")
    public Mono<SimpleEvent> emitEventById(@PathVariable long id) {
        return Mono.just(new SimpleEvent(id, "Hello, world!"));
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/events")
    public Flux<SimpleEvent> emitEvents() {
        Flux<SimpleEvent> eventFlux = Flux.fromStream(Stream.generate(() -> {
            int id = new Random().nextInt();
            return new SimpleEvent(id, "Event: " + id);
        })).delayElements(Duration.ofSeconds(1));
        return eventFlux;
    }
}
