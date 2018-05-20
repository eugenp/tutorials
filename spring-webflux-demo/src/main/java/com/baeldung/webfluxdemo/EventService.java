package com.baeldung.webfluxdemo;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@Service
public class EventService {

	public Flux<Event> getEvents() {
		
		Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1));
		
		Flux<Event> events = Flux.fromStream(Stream.generate(() -> new Event(String.valueOf(System.currentTimeMillis()), new Date())));
		
		Flux<Tuple2<Event, Long>> zip = Flux.zip(events, intervalFlux);
		
		return zip.map( t -> t.getT1()); 
	}

}
