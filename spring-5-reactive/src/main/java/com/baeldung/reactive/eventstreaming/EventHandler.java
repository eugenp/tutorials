package com.baeldung.reactive.eventstreaming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class EventHandler {

	@Autowired
	EventEmitter eventEmitter;

	public Mono<ServerResponse> eventEmitted(ServerRequest request) {
		return ServerResponse.ok()
			.contentType(MediaType.APPLICATION_STREAM_JSON)
			.body(eventEmitter.emitEvent(), Event.class);
	}
}
