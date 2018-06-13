package com.baeldung.webflux.reactiveapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@SpringBootApplication
public class ReactiveServerApplication {

    @Bean
    RouterFunction<?> route(EventHandler handler) {
        return RouterFunctions.
                route(RequestPredicates.GET("/emitEvents"), handler::getEvents);
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveServerApplication.class, args);
    }
}
