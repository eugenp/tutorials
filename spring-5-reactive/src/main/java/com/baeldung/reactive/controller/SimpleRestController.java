package com.baeldung.reactive.controller;

import java.time.Duration;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.reactive.model.Person;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class SimpleRestController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, path = "/streaming-data")
    public Flux<Long> getStreamingData() {
        return Flux.interval(Duration.ofSeconds(1));
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, path = "/streaming-persons-data")
    public Flux<Person> getStreamingPersonsData() {
        List<Person> persons = null;
        Flux<Person> personFlux = Flux.fromIterable(persons);
        Flux<Long> sequenceFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(personFlux, sequenceFlux)
            .map(Tuple2::getT1);
    }
}
