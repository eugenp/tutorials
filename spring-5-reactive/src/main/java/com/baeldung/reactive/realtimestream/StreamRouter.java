package com.baeldung.reactive.realtimestream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class StreamRouter {

    @Bean
    RouterFunction<ServerResponse> streamEvents(StreamHandler streamHandler) {
        return RouterFunctions.route(
                RequestPredicates.GET("/events"),
                streamHandler::stream
        );
    }

}
