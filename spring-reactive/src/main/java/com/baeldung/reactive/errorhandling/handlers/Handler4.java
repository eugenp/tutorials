package com.baeldung.reactive.errorhandling.handlers;

import com.baeldung.reactive.errorhandling.NameRequiredException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class Handler4 {

public Mono<ServerResponse> handleRequest4(ServerRequest request) {
    return ServerResponse.ok()
        .body(sayHello(request)
            .onErrorResume(e -> 
                    Mono.error(new NameRequiredException(
                        HttpStatus.BAD_REQUEST, "please provide a name", e))), String.class);
}

    private Mono<String> sayHello(ServerRequest request) {
        try {
            return Mono.just("Hello, " + request.queryParam("name").get());
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

}
