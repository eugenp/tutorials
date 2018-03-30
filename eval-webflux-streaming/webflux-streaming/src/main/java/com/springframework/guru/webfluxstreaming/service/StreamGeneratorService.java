package com.springframework.guru.webfluxstreaming.service;

import java.time.Duration;
import java.util.Random;
import java.util.function.BiFunction;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

@Service
public class StreamGeneratorService {
	
	public Flux<Integer> streamGen(){
		Random rand = new Random();

		return Flux.generate(/*Simplified Consumer*/()->0 , 
				/* Value Generator */ 
				(BiFunction <Integer, SynchronousSink<Integer>, Integer>) (index, sink) -> {
					Integer number =Integer.valueOf(rand.nextInt(50)); // Generate a random value
					sink.next(number); // Add to Sink
					return number; // Return the generated value
				})
				.zipWith(Flux.interval(Duration.ofSeconds(1l))) // Generate one value per second
				.map(t -> t.getT1()); // Return the first object which is the generated number
	}
}
