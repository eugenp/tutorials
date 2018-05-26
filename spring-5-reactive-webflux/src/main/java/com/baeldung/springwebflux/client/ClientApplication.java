package com.baeldung.springwebflux.client;

import com.baeldung.springwebflux.model.Equity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ClientApplication {
    public static void main(String[] args) throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080");
        webClient.get()
                .uri("/api/getEquityPrice")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange().flatMapMany(clientResponse -> {
            return clientResponse.bodyToFlux(Equity.class);
        }).log().blockLast();

        while (true) {
            // To block "main" from exiting.
        }
    }
}
