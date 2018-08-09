package com.baeldung.eventstreaming.server;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class EventRouter {

    @Bean
    public RouterFunction<ServerResponse> route(EventHandler eventHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/events")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)),
                eventHandler::eventEmitted);
    }
}