package com.baeldung.reactive.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import com.baeldung.reactive.model.Stock;

import reactor.core.publisher.Flux;

public class StockClient {

    public Flux<Stock> getStockUpdates(String stockCode) {
        WebClient client = WebClient.create("localhost:8080");
        RequestHeadersSpec<?> request = client.get()
            .uri("/rtes/stocks/" + stockCode)
            .accept(MediaType.TEXT_EVENT_STREAM);
        return request.retrieve()
            .bodyToFlux(Stock.class)
            .log();
    }
}
