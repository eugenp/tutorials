package com.baeldung.springwebflux.client;

import com.baeldung.springwebflux.model.Equity;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class ClientApplication {
    public static void main(String[] args) throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080");
        Flux<Equity> equityResponseFlux = webClient.get()
            .uri("/api/getEquityPrice")
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .retrieve()
            .bodyToFlux(Equity.class);
        while (true) {
            equityResponseFlux.subscribe(equity -> {
                System.out.println(equity.getEquityId() + " & " + equity.getEquityPrice());
            });
        }
    }
}
