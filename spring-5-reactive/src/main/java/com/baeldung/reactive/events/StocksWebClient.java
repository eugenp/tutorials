package com.baeldung.reactive.events;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class StocksWebClient {

    WebClient client = WebClient.create("http://localhost:8080");

    public void getStockPrices() {

        Flux<Integer> stockPrices = client.get()
            .uri("/stockprices")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(Integer.class);

        stockPrices.subscribe(System.out::println);

    }
}
