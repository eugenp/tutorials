package com.baeldung.functional.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import com.baeldung.functional.handler.NearestAirportHandler;

@Configuration
public class AllRoutes {

    private NearestAirportHandler nearestAirportHandler;

    public AllRoutes(NearestAirportHandler nearestAirportHandler) {
        this.nearestAirportHandler = nearestAirportHandler;
    }

    @Bean
    public RouterFunction<?> routerFunction() {
        return
                route(GET("/api/city").and(accept(MediaType.APPLICATION_JSON)), nearestAirportHandler::handleGetCityAirports)
                .and(route(GET("/api/city/{city}").and(accept(MediaType.APPLICATION_JSON)), nearestAirportHandler::handleGetAirportByCityName));

    }
}