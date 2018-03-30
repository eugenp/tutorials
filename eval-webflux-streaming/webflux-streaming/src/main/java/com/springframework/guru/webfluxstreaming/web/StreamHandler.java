package com.springframework.guru.webfluxstreaming.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.springframework.guru.webfluxstreaming.service.StreamGeneratorService;

import reactor.core.publisher.Mono;

@Component
public class StreamHandler {

	public Mono<ServerResponse> getStreamOfNumberEvents(ServerRequest serverRequest) {
		return ServerResponse
				.ok() // Set ResponseStatus to 200
				.contentType(MediaType.APPLICATION_STREAM_JSON) // return Stream JSON value
				.body(this.streamGenSvc.streamGen(),Integer.class); // Add in body the value obtained from service layer
	}
	
	private final StreamGeneratorService streamGenSvc;

	public StreamHandler(StreamGeneratorService streamGenSvc) {
		this.streamGenSvc = streamGenSvc;
	}
}
