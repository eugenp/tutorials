package com.baeldung.server.reactivestreams.controller;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
public class CustomEventController {

@GetMapping("/getevent")
public Mono<String> getEvent(){
	return Mono.just("Event occurred at "+(new Date()));
}

@GetMapping(value="/allevents", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> getAllEvents(){
	Flux<String> customEventFlux = Flux.fromStream(Stream.generate( ()->(new String("Event occurred at "+(new Date()))) ));
	Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1));
	return Flux.zip(customEventFlux, intervalFlux).map(Tuple2::getT1);
}
}
