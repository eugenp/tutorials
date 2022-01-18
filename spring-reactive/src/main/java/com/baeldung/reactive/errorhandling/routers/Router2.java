package com.baeldung.reactive.errorhandling.routers;

import com.baeldung.reactive.errorhandling.handlers.Handler2;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class Router2 {

    @Bean
    public RouterFunction<ServerResponse> routeRequest2(Handler2 handler) {
        return RouterFunctions.route(RequestPredicates.GET("/api/endpoint2")
            .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::handleRequest2);
    }

}
