package com.baeldung.memoization;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoizationWithMonoCache {

    static WebClient client = WebClient.create("https://jsonplaceholder.typicode.com/users");

    static AtomicInteger counter = new AtomicInteger(0);

    static Mono<User> retrieveOneUser(int id) {
        return client.get()
            .uri("/{id}", id)
            .retrieve()
            .bodyToMono(User.class)
            .doOnSubscribe(i -> counter.incrementAndGet())
            .onErrorResume(Mono::error);
    }

    int getCounter() {
        return counter.get();
    }
}
