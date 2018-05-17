package com.baeldung.reactive.streaming.functional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class SequenceExampleRouter {

    @Bean
    public RouterFunction<ServerResponse> route(SequenceExampleHandler sequenceExampleHandler) {
        return RouterFunctions
                .route(
                    GET("/sequence").and(RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)),
                    sequenceExampleHandler::sequence);
    }

}
