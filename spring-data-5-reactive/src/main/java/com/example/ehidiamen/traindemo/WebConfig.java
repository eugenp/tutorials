package com.example.ehidiamen.traindemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import com.example.ehidiamen.traindemo.handler.TrainArrivalHandler;
import com.example.ehidiamen.traindemo.handler.TrainStationHandler;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {
	
	@Bean
	public RouterFunction routeTrains(TrainStationHandler trainStationHandler, TrainArrivalHandler trainArrivalHandler) {
		return RouterFunctions
				.route(RequestPredicates.GET("/trains/{id}/arrivals"), trainArrivalHandler::arrivals)
				.andRoute(RequestPredicates.GET("/trains/{id}"), trainStationHandler::getId);
	}	
}
