package com.baeldung.reactive.webclient.simultaneous;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class Client {

    private WebClient webClient;

    public Client(String uri) {
        this.webClient = WebClient.create(uri);
    }

    public Mono<User> getUser(int id) {
        return webClient.get()
            .uri("/user/{id}", id)
            .retrieve()
            .bodyToMono(User.class);
    }

    public Mono<Item> getItem(int id) {
        return webClient.get()
            .uri("/item/{id}", id)
            .retrieve()
            .bodyToMono(Item.class);
    }

    public Mono<User> getOtherUser(int id) {
        return webClient.get()
            .uri("/otheruser/{id}", id)
            .retrieve()
            .bodyToMono(User.class);
    }

    public List<User> fetchUsers(List<Integer> userIds) {
        return Flux.fromIterable(userIds)
            .parallel()
            .runOn(Schedulers.elastic())
            .flatMap(this::getUser)
            .collectSortedList((u1, u2) -> u2.id() - u1.id())
            .block();
    }

    public List<User> fetchUserAndOtherUser(int id) {
        return Flux.merge(getUser(id), getOtherUser(id))
            .parallel()
            .runOn(Schedulers.elastic())
            .collectSortedList((u1, u2) -> u2.id() - u1.id())
            .block();
    }

    public UserWithItem fetchUserAndItem(int userId, int itemId) {
        Mono<User> user = getUser(userId).subscribeOn(Schedulers.elastic());
        Mono<Item> item = getItem(itemId).subscribeOn(Schedulers.elastic());

        return Mono.zip(user, item, UserWithItem::new)
            .block();
    }
}
