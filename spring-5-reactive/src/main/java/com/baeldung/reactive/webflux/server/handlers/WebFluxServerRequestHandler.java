package com.baeldung.reactive.webflux.server.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class WebFluxServerRequestHandler { 
    public Mono<ServerResponse> sayHello(ServerRequest request) {
        return ServerResponse.ok().body(Mono.just("hello"), String.class);
    }
}
