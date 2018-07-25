package com.baeldung.webflux.controller;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.webflux.model.Event;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class WebFluxController {

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Event> getEvent() {

	Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
	Flux<Event> stockTransactionFlux = Flux.fromStream(Stream
		.generate(() -> new Event(System.currentTimeMillis(), "Spring WebFlux! Controller generated event!")));
	Flux.just(new Event(System.currentTimeMillis(), "Spring WebFlux! Controller generated event!"));
	return Flux.zip(interval, stockTransactionFlux).map(Tuple2::getT2);

    }
}
