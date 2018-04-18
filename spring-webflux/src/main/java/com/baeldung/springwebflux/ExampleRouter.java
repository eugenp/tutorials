package com.baeldung.springwebflux;

import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class ExampleRouter {

    @Bean
    public RouterFunction<ServerResponse> route(ExampleHandler handler) {
        return RouterFunctions.route(
                RequestPredicates.GET("/example").and(RequestPredicates.accept(MediaType.TEXT_PLAIN))
                , handler::hello
        );
    }
}
