package com.baeldung.debugging.server.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.debugging.server.handlers.ServerHandler;

@Configuration
public class ServerRouter {

	@Bean
	public RouterFunction<ServerResponse> responseRoute(@Autowired ServerHandler handler) {
		return RouterFunctions.route(RequestPredicates.GET("/functional-reactive/periodic-foo"), handler::useHandler)
				.andRoute(RequestPredicates.GET("/functional-reactive/periodic-foo-2"), handler::useHandlerFinite);
	}

}
