package com.baeldung.reactive.errorhandling.handlers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class Handler3 {

    public Mono<ServerResponse> handleRequest3(ServerRequest request) {
        return 
            sayHello(request)
              .flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(s))
              .onErrorResume(e -> (Mono.just("Hi, I looked around for your name but found: " + 
                    e.getMessage())).flatMap(s -> ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .bodyValue(s)));
    }

    private Mono<String> sayHello(ServerRequest request) {
        try {
            return Mono.just("Hello, " + request.queryParam("name")
                .get());
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

}
