package com.baeldung.reactive.filters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class PlayerRouter {

    @Bean
    public RouterFunction<ServerResponse> route(PlayerHandler playerHandler) {
        return RouterFunctions
                .route(GET("/players/{name}"), playerHandler::getName)
                .filter(new ExampleHandlerFilterFunction());
    }
}
