package com.baeldung.reactive.events;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@Service
public class StockPriceService 
{
	public Flux<String> getStockPrice()
    {
        Flux<String> event = Flux.fromStream(Stream.generate(() -> generateMessage()));
        Flux<Long> duration = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(event, duration).map(Tuple2::getT1);
    }
		    
    private String generateMessage()
    {
    	Random r = new Random();
        String message = "Updated price is: " + Math.abs(r.nextInt()+1);
        return message;
    }
}
