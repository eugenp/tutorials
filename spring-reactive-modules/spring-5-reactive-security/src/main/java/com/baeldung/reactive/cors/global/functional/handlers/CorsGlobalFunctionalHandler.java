package com.baeldung.reactive.cors.global.functional.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class CorsGlobalFunctionalHandler {

    public Mono<ServerResponse> useHandler(final ServerRequest request) {
        final String responseMessage = "CORS GLOBAL CONFIG IS NOT EFFECTIVE ON FUNCTIONAL ENDPOINTS!!!";

        return ServerResponse.ok()
            .body(Mono.just(responseMessage), String.class);
    }
}
