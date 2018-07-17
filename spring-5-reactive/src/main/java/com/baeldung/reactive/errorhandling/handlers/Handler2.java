
package com.baeldung.reactive.errorhandling.handlers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class Handler2 {

public Mono<ServerResponse> handleRequest2(ServerRequest request) {
    return 
        sayHello(request)
            .flatMap(s -> ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .syncBody(s))
            .onErrorResume(e -> sayHelloFallback()
                .flatMap(s -> ServerResponse.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .syncBody(s)));
}

    private Mono<String> sayHello(ServerRequest request) {
        try {
            return Mono.just("Hello, " + request.queryParam("name")
                .get());
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    private Mono<String> sayHelloFallback() {
        return Mono.just("Hello, Stranger");
    }

}
