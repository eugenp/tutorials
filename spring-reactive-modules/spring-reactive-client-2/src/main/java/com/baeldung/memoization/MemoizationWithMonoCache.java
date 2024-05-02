package com.baeldung.memoization;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoizationWithMonoCache {

    WebClient client = WebClient.create("https://jsonplaceholder.typicode.com/users");

    AtomicInteger counter = new AtomicInteger(0);

    public  Mono<User> retrieveOneUser(int id) {
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
