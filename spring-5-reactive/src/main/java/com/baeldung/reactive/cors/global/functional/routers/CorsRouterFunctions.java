package com.baeldung.reactive.cors.global.functional.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.reactive.cors.global.functional.handlers.CorsGlobalFunctionalHandler;

@Configuration
public class CorsRouterFunctions {

    @Bean
    public RouterFunction<ServerResponse> corsGlobalRouter(@Autowired CorsGlobalFunctionalHandler handler) {
        return RouterFunctions.route(RequestPredicates.PUT("/global-config-on-functional/cors-disabled-functional-endpoint"), handler::useHandler);
    }
}
