package com.baeldung.limitrequests.client;

import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.limitrequests.client.utils.Client;
import com.baeldung.limitrequests.client.utils.RandomConsumer;
import com.google.common.util.concurrent.RateLimiter;

import reactor.core.publisher.Flux;

public class GuavaRateLimit {

    private GuavaRateLimit() {
    }

    public static Flux<Integer> fetch(WebClient client, int requests, double limit) {
        String clientId = Client.id(requests, GuavaRateLimit.class, limit);

        RateLimiter limiter = RateLimiter.create(limit);

        return Flux.range(1, requests)
            .log()
            .flatMap(i -> {
                limiter.acquire();

                return RandomConsumer.get(client, clientId);
            });
    }

    public static void main(String[] args) throws InterruptedException {
        String baseUrl = args[0];
        WebClient client = WebClient.create(baseUrl);

        fetch(client, 20, 2).doOnNext(System.out::println)
            .blockLast();
    }
}
