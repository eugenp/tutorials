package com.baeldung.springwebflux.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.springwebflux.model.Equity;

public class ClientApplication {
    public static void main(String[] args) throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080");
        webClient.get()
            .uri("/api/getEquityPrice")
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .flatMapMany(clientResponse -> {
                return clientResponse.bodyToFlux(Equity.class);
            })
            .log()
            .blockLast();

        while (true) {
            // To block "main" from exiting.
        }
    }
}
