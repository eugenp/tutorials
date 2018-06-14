package com.baeldung.reactive.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class SimpleRouter {

	@Bean
	public RouterFunction<ServerResponse> route(SimpleHandler handler) {
		return RouterFunctions.route(
				RequestPredicates.GET("/events").and(RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)),
				handler::getEvent);
	}

}
