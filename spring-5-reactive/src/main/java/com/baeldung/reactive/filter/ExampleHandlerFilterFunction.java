package com.baeldung.reactive.filter;

import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class ExampleHandlerFilterFunction implements HandlerFilterFunction {

    @Override
    public Mono filter(ServerRequest serverRequest, HandlerFunction handlerFunction) {
        if (serverRequest.pathVariable("name").equalsIgnoreCase("test")) {
            return ServerResponse.status(FORBIDDEN).build();
        }
        return handlerFunction.handle(serverRequest);
    }

}

