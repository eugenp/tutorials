package com.baeldung.reactive.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class Handler {

    public Mono<ServerResponse> handleWithErrorReturn(ServerRequest request) {
        return sayHello(request)
          .onErrorReturn("Hello, Stranger")
          .flatMap(s -> ServerResponse.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .bodyValue(s));
    }

    public Mono<ServerResponse> handleWithErrorResumeAndDynamicFallback(ServerRequest request) {
        return sayHello(request)
          .flatMap(s -> ServerResponse.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .bodyValue(s))
          .onErrorResume(e -> (Mono.just("Hi, I looked around for your name but found: " + e.getMessage()))
            .flatMap(s -> ServerResponse.ok()
              .contentType(MediaType.TEXT_PLAIN)
              .bodyValue(s)));
    }

    public Mono<ServerResponse> handleWithErrorResumeAndFallbackMethod(ServerRequest request) {
        return sayHello(request)
          .flatMap(s -> ServerResponse.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .bodyValue(s))
          .onErrorResume(e -> sayHelloFallback()
            .flatMap(s -> ServerResponse.ok()
              .contentType(MediaType.TEXT_PLAIN)
              .bodyValue(s)));
    }

    public Mono<ServerResponse> handleWithErrorResumeAndCustomException(ServerRequest request) {
        return ServerResponse.ok()
          .body(sayHello(request)
            .onErrorResume(e -> Mono.error(new NameRequiredException(
              HttpStatus.BAD_REQUEST,
              "please provide a name", e))), String.class);
    }

    public Mono<ServerResponse> handleWithGlobalErrorHandler(ServerRequest request) {
        return ServerResponse.ok()
          .body(sayHello(request), String.class);
    }

    private Mono<String> sayHello(ServerRequest request) {
        try {
            return Mono.just("Hello, " + request.queryParam("name").get());
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    private Mono<String> sayHelloFallback() {
        return Mono.just("Hello, Stranger");
    }
}
