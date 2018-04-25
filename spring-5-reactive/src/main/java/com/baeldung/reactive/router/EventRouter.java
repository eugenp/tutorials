package com.baeldung.reactive.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.reactive.handler.EventHandler;

@Configuration
@EnableWebFlux
public class EventRouter implements WebFluxConfigurer {

	@Bean
	public RouterFunction<ServerResponse> routeEvents(EventHandler eventHandler) {
		return RouterFunctions
				.route(RequestPredicates.GET("/events"), eventHandler::getEvents);
	}

}
