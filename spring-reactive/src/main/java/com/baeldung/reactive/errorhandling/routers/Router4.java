package com.baeldung.reactive.errorhandling.routers;

import com.baeldung.reactive.errorhandling.handlers.Handler4;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class Router4 {

    @Bean
    public RouterFunction<ServerResponse> routeRequest4(Handler4 handler) {
        return RouterFunctions.route(RequestPredicates.GET("/api/endpoint4")
            .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::handleRequest4);
    }

}
