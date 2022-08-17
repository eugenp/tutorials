package com.baeldung.reactive.cors.webfilter.functional.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class CorsWithWebFilterHandler {

    public Mono<ServerResponse> useHandler(final ServerRequest request) {
        return ServerResponse.ok()
            .body(Mono.just("Functional Endpoint"), String.class);
    }
}
