package com.baeldung.reactive.webflux;

import java.util.UUID;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class EventGenerator {

	public EventGenerator() {
	}

	public Flux<Event> generateEvent() {
		return Flux.<Event>create(fluxSink -> {
			while (true) {
				fluxSink.next(EventUtils.triggerEvent());
			}
		}).share();
	}
	
	public static class EventUtils{
		public static Event triggerEvent()
		{
			return new Event(UUID.randomUUID().toString());
		}
	}
	
}
