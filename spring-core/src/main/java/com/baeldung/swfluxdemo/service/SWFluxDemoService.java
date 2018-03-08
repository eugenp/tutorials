package com.baeldung.swfluxdemo.service;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

/**
 * @author Robert B. Andrews 1.0 3/7/18
 *
 */
@Service
public class SWFluxDemoService {

	public Flux<String> getInfinityString() {

		Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
		interval.subscribe((i) -> generateNewMessage());
		Flux<String> messageFlux = Flux.fromStream(Stream.generate(() -> generateNewMessage()));

		return Flux.zip(interval, messageFlux).map(Tuple2::getT2);

	}

	private String generateNewMessage() {
		Date theDate = new Date();
		String newMessage = "Updated response at: " + theDate.toString() + "<BR>";
		return newMessage;

	}
}
