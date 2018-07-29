package com.baeldung.reactive.webflux.eval;

import org.springframework.web.reactive.function.client.WebClient;

public class PurchaseReactiveClient {
    public static void consume() {
        WebClient.create()
            .get()
            .uri("http://localhost:9001/purchases")
            .exchange()
            .flatMapMany(resp -> resp.bodyToFlux(Purchase.class))
            .subscribe(
                System.out::println,
                System.err::println
            );
    }

    public static void main(String... args) {
        consume();
    }
}
