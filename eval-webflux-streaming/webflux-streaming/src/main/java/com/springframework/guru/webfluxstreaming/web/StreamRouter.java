package com.springframework.guru.webfluxstreaming.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class StreamRouter {

	@Bean
	public RouterFunction<ServerResponse> route(StreamHandler streamHandler) {
		return RouterFunctions
				.route(GET("/getStreamEvent")
						.and(accept(MediaType.APPLICATION_STREAM_JSON)),
						streamHandler::getStreamOfNumberEvents);
	}

}
