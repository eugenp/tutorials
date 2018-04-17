package com.baeldung.reactive.controller;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.reactive.model.Event;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class ReactiveEventController {

	@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Event> getEvents() {
		final Flux<Event> eventFlux = Flux
				.fromStream(Stream.generate(() -> new Event(new Random().nextLong(), "Hello Event")));
		final Flux<Long> emmitFlux = Flux.interval(Duration.ofSeconds(1));
		return Flux.zip(eventFlux, emmitFlux).map(Tuple2::getT1);
	}
}
