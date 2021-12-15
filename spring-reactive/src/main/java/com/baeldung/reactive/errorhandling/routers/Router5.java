package com.baeldung.reactive.errorhandling.routers;

import com.baeldung.reactive.errorhandling.handlers.Handler5;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class Router5 {

    @Bean
    public RouterFunction<ServerResponse> routeRequest5(Handler5 handler) {
        return RouterFunctions.route(RequestPredicates.GET("/api/endpoint5")
            .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::handleRequest5);
    }

}
