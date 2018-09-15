package com.example.demo.service;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class NumberEventService {
	private int number = 0;

	public Flux<Integer> generateNumberEvent() {
		return Flux.fromStream(Stream.generate(() -> number++).map(s -> Integer.valueOf(s)))
				.delayElements(Duration.ofSeconds(1));
	}

	
}
