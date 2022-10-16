package com.baeldung.limitrequests.client;

import java.time.Duration;

import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.limitrequests.client.utils.Client;
import com.baeldung.limitrequests.client.utils.RandomConsumer;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import reactor.core.publisher.Flux;

public class Resilience4jRateLimit {

    private Resilience4jRateLimit() {
    }

    public static Flux<Integer> fetch(WebClient client, int requests, int concurrency, int interval) {
        RateLimiter limiter = RateLimiter.of("my-rate-limiter", RateLimiterConfig.custom()
            .limitRefreshPeriod(Duration.ofMillis(interval))
            .limitForPeriod(concurrency)
            .timeoutDuration(Duration.ofMillis((long) interval * concurrency))
            .build());

        String clientId = Client.id(requests, Resilience4jRateLimit.class, concurrency, interval);

        return Flux.range(1, requests)
            .log()
            .flatMap(i -> RandomConsumer.<Integer> get(client, clientId)
                .transformDeferred(RateLimiterOperator.of(limiter)));
    }

    public static void main(String[] args) {
        String baseUrl = args[0];
        WebClient client = WebClient.create(baseUrl);

        fetch(client, 10, 5, 2500).doOnNext(System.out::println)
            .blockLast();
    }
}