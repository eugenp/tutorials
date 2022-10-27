package com.baeldung.spring.reactive.springreactiveexceptions.handler;

import com.baeldung.spring.reactive.springreactiveexceptions.model.Users;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class DataProcessingHandler implements HandlerFunction<ServerResponse> {
	@Override
	public Mono<ServerResponse> handle(ServerRequest request) {
		return ServerResponse
		  .ok()
		  .body(request
			.bodyToMono(Users.class), Users.class);
	}
}
