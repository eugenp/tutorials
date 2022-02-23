package com.baeldung.reactive.errorhandling.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class Handler5 {

    public Mono<ServerResponse> handleRequest5(ServerRequest request) {
        return ServerResponse.ok()
            .body(sayHello(request), String.class);
        
    }

    private Mono<String> sayHello(ServerRequest request) {
            return Mono.just("Hello, " + request.queryParam("name").get());
    }

}
