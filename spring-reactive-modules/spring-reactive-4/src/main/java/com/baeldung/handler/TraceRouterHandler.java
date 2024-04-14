package com.baeldung.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class TraceRouterHandler {

    public Mono<ServerResponse> handle(final ServerRequest serverRequest) {

        final String traceId = serverRequest.headers()
            .firstHeader("traceId");
        assert traceId != null;
        final Mono<String> body = Mono.just("TraceId: ".concat(traceId));
        return ok().body(body, String.class);
    }
}
