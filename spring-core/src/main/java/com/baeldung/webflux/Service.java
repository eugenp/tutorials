package com.baeldung.webflux;

import java.util.Date;
import java.util.stream.Stream;
import reactor.core.publisher.Flux;

public class Service {
	
	Flux<String> getStringFlux(){
		
	    Flux<String> f = Flux
	    	.fromStream(Stream.generate(() -> 
	    	"Price of Gold: $" +
	    	String.valueOf(Gold.getPrice()) +
	    	"\t When: "+ new Date()));
	    return f;
	}

}
