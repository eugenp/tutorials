package com.baeldung.webflux.responsestatus;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/statuses")
@RestController
public class ResponseStatusController {

    @GetMapping(value = "/ok", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> ok() {
        return Flux.just("ok");
    }

    @GetMapping(value = "/no-content", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Flux<String> noContent() {
        return Flux.empty();
    }

    @GetMapping(value = "/accepted", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> accepted(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.ACCEPTED);
        return Flux.just("accepted");
    }

    @GetMapping(value = "/bad-request")
    public Mono<String> badRequest() {
        return Mono.error(new IllegalArgumentException());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal arguments")
    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgument() {

    }

    @GetMapping(value = "/unauthorized")
    public ResponseEntity<Mono<String>> unathorized() {
        return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .header("X-Reason", "user-invalid")
          .body(Mono.just("unauthorized"));
    }

    @Bean
    public RouterFunction<ServerResponse> notFound() {
        return RouterFunctions.route(GET("/statuses/not-found"), request -> ServerResponse
          .notFound()
          .build());
    }

}
