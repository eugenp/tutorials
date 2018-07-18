package com.baeldung.webflux.reactive.repository;

import com.baeldung.webflux.reactive.model.StockQuote;
import reactor.core.publisher.Flux;

public interface StockExchange {
    public Flux<StockQuote> trading();
}
