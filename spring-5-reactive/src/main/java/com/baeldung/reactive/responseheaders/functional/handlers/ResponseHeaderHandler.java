package com.baeldung.reactive.responseheaders.functional.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class ResponseHeaderHandler {
    
    public Mono<ServerResponse> useHandler(final ServerRequest request) {
        String responseHeaderKey = "Baeldung-Example-Header";
        String responseHeaderValue = "Value-Handler";
        String responseBody = "Response with header using Handler";

        return ServerResponse.ok()
            .header(responseHeaderKey, responseHeaderValue)
            .body(Mono.just(responseBody),String.class);
    }
}
