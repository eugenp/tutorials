package com.baeldung.reactive.cors.webfilter.functional.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.reactive.cors.webfilter.functional.handlers.CorsWithWebFilterHandler;

@Configuration
public class CorsWithWebFilterRouterFunctions {

    @Bean
    public RouterFunction<ServerResponse> corsWebfilterRouter(@Autowired CorsWithWebFilterHandler handler) {
        return RouterFunctions.route(RequestPredicates.PUT("/web-filter-on-functional/functional-endpoint"), handler::useHandler);
    }
}
