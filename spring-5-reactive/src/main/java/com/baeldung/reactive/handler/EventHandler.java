package com.baeldung.reactive.handler;

import java.time.Duration;
import java.util.Random;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.reactive.model.Event;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EventHandler {

	public Mono<ServerResponse> getEvents(ServerRequest serverRequest) {
		Flux<Event> events = Flux.<Event>generate(sink -> sink.next(new Event(new Random().nextLong(), "Hello Event"))).delayElements(Duration.ofSeconds(1));
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(events, Event.class);
	}

}
