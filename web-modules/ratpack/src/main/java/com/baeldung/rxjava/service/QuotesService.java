package com.baeldung.rxjava.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

import org.reactivestreams.Publisher;

import com.baeldung.model.Quote;

import ratpack.stream.Streams;

public class QuotesService {

    private final ScheduledExecutorService executorService;
    private static Random rnd = new Random(); 
    private static String[] symbols = new String[] {
      "MSFT",
      "ORCL",
      "GOOG",
      "AAPL",
      "CSCO"
    };
    
    public QuotesService(ScheduledExecutorService executorService) {
        this.executorService = executorService;
    }

    public Publisher<Quote> newTicker() {
        return Streams.periodically(executorService, Duration.ofSeconds(2), (t) -> {
           
            return randomQuote();
        });
    }
    
    private static Quote randomQuote() {
        return new Quote (
            Instant.now(),
            symbols[rnd.nextInt(symbols.length)],
            Math.round(rnd.nextDouble()*100)
        );
    }
}
