package com.baeldung.limitrequests.client;

import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.limitrequests.client.utils.Client;
import com.baeldung.limitrequests.client.utils.RandomConsumer;

import reactor.core.publisher.Flux;

public class LimitConcurrency {

    private LimitConcurrency() {
    }

    public static Flux<Integer> fetch(WebClient client, int requests, int concurrency) {
        String clientId = Client.id(requests, LimitConcurrency.class.getSimpleName(), concurrency);

        return Flux.range(1, requests)
            .log()
            .flatMap(i -> RandomConsumer.get(client, clientId), concurrency);
    }

    public static void main(String[] args) throws InterruptedException {
        String baseUrl = args[0];
        WebClient client = WebClient.create(baseUrl);

        fetch(client, 12, 5).doOnNext(System.out::println)
            .blockLast();
    }
}
