package com.baeldung.reactive.filters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class EventRouter {

    @Bean
    public RouterFunction<ServerResponse> routeEvent(EventHandler handler) {
        return RouterFunctions.route(RequestPredicates.POST("/event"), handler::handle);
    }
}
