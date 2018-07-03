package com.baeldung.functional.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import com.baeldung.functional.handler.FurthestAirportHandler;
import com.baeldung.functional.handler.NearestAirportHandler;

@Configuration
public class AllRoutes {

    private NearestAirportHandler nearestAirportHandler;
    
    private FurthestAirportHandler furthestAirportHandler;

    public AllRoutes(NearestAirportHandler nearestAirportHandler, FurthestAirportHandler furthestAirportHandler) {
        this.nearestAirportHandler = nearestAirportHandler;
        this.furthestAirportHandler = furthestAirportHandler;
    }

    @Bean
    public RouterFunction<?> routerFunction() {
        return
                route(GET("/api/nearestcity").and(accept(MediaType.APPLICATION_JSON)), nearestAirportHandler::handleGetCityAirports)
           .and(route(GET("/api/nearestcity/{city}").and(accept(MediaType.APPLICATION_JSON)), nearestAirportHandler::handleGetAirportByCityName))
           .and(route(GET("/api/furthestcity").and(accept(MediaType.APPLICATION_JSON)), furthestAirportHandler::handleGetCityAirports))
           .and(route(GET("/api/furthestcity/{city}").and(accept(MediaType.APPLICATION_JSON)), furthestAirportHandler::handleGetAirportByCityName));

    }
}