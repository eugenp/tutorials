package com.baeldung.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterClass {

	@Bean
	public RouterFunction<ServerResponse> routeExample (HandlerClass handler) {
		
		return RouterFunctions
			.route(RequestPredicates.GET("/check").
			and(RequestPredicates.accept(MediaType.TEXT_EVENT_STREAM)), handler::check);
	}
}
