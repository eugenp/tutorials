package com.baeldung.reactive.webflux;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@Service
public class EventService {

	public Flux<EventData> publishEvent() {
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
		Flux<EventData> eventTransactionFlux = Flux
				.fromStream(Stream.generate(() -> new EventData( UUID.randomUUID().toString(), new Date())));
		return Flux.zip(interval, eventTransactionFlux).map(Tuple2::getT2);
	}

}
