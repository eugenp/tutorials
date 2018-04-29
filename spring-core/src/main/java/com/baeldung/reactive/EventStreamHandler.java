package com.baeldung.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class EventStreamHandler {

    @Autowired
    EventStreamer eventStreamer;

    public Mono<ServerResponse> events(ServerRequest sr) {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_STREAM_JSON)
            .body(eventStreamer.start(), Event.class);
    }
}
