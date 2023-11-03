package com.baeldung.reactive.responseheaders.functional.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.reactive.responseheaders.functional.handlers.ResponseHeaderHandler;

@Configuration
public class ResponseHeadersRouterFunctions {

    @Bean
    public RouterFunction<ServerResponse> responseHeaderRoute(@Autowired ResponseHeaderHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/functional-response-header/single-handler"), handler::useHandler);
    }
}
