package com.baeldung.reactive.alex.comsa.functional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FlightRouter {

    @Bean
    public RouterFunction<ServerResponse> route(FlightHandler handler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/functional/flight").
                                and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        handler::getFlightData);
    }
}
