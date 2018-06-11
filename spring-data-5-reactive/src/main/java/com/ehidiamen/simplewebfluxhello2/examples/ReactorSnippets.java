package com.ehidiamen.simplewebfluxhello2.examples;

import java.util.Arrays;
import java.util.List;
import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorSnippets {
	private static List<String> words = Arrays.asList(
	        "the",
	        "quick",
	        "brown",
	        "fox",
	        "jumped",
	        "over",
	        "the",
	        "lazy",
	        "dog"
	        );
	@Test
	public void shortCircuit() {
	  Flux<String> helloPauseWorld = 
	              Mono.just("Hello")
	                  .concatWith(Mono.just("world")
	                  .delaySubscription(Duration.ofMillis(500)));
	  

	  helloPauseWorld.toStream()
      	.forEach(System.out::println);
	}
	
	  
	  public void simpleCreation() {
		 Mono<String> missing = Mono.just("s");
	     Flux<String> fewWords = Flux.just("Hello", "World");
	     Flux<String> manyWords = Flux.
	    		 fromIterable(words)
	    		 .flatMap(word -> Flux.fromArray(word.split("")))
	    		 .concatWith(missing)
	    	        .distinct()
	    	        .sort()
	    	        .zipWith(Flux.range(1, Integer.MAX_VALUE),
	    	              (string, count) -> String.format("%2d. %s", count, string));

	     fewWords.subscribe(System.out::println);
	     System.out.println();
	     manyWords.subscribe(System.out::println);
	     
	  }
	
}
