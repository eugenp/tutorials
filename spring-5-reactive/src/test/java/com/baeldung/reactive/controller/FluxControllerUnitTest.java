package com.baeldung.reactive.controller;

import reactor.core.publisher.Flux;

import org.junit.Test;

public class FluxControllerUnitTest {
	
	@Test
	public void consumeFlux() throws InterruptedException {
		FluxController fluxController = new FluxController();
		Flux<Long> longFlux = fluxController.helloFlux();
		System.out.println("Flux arrived. Thread id: " + Thread.currentThread().getId());
		longFlux.log().subscribe(l -> System.out.println("The message: " + l + ". Thread id: " + Thread.currentThread().getId()));
		Thread.sleep(5000);
	}
	
}
