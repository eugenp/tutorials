package com.baeldung.webflux;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Component
public class HandlerClass {

	public Mono<ServerResponse> check(ServerRequest request) {
		
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        interval.subscribe((i) -> i.intValue());
        Service s= new Service();
        Flux<String> fString= s.getStringFlux();
        
        Mono<ServerResponse> response =
        	ServerResponse.ok().
        	contentType(MediaType.TEXT_EVENT_STREAM).
        	body(Flux.zip(interval, fString).
        	map(Tuple2::getT2), String.class);
        return response; 
	}
}
