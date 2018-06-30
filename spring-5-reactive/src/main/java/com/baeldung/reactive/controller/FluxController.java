package com.baeldung.reactive.controller;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FluxController {
	
	@GetMapping("/helloFlux")
	public Flux<Long> helloFlux() {
		return Flux.interval(Duration.of(1, ChronoUnit.SECONDS));
	}
}
