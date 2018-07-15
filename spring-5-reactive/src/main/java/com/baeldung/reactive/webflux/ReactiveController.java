package com.baeldung;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
@SpringBootApplication
public class ReactiveController {

    @GetMapping(value = "/serverSentEvents", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Date> getEvent() {
        Flux<Date> dateFlux = Flux.fromStream(Stream.generate(() -> new Date()));
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(dateFlux, intervalFlux)
            .map(Tuple2::getT1);
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveController.class, args);
    }
}
