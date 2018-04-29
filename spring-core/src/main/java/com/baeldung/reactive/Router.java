package com.baeldung.reactive;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class Router {

    @Bean
    public RouterFunction<ServerResponse> routeEvents(EventStreamHandler streamHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/events")
            .and(RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)), streamHandler::events);
    }
}
