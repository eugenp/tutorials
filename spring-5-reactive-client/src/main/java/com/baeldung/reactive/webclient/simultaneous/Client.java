package com.baeldung.reactive.webclient.simultaneous;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Logger;

public class Client {

    private static final Logger LOG = Logger.getLogger(Client.class.getName());

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

    public Flux<User> fetchUsers(List<Integer> userIds) {
        return Flux.fromIterable(userIds)
            .flatMap(this::getUser);
    }

    public Flux<User> fetchUserAndOtherUser(int id) {
        return Flux.merge(getUser(id), getOtherUser(id));
    }

    public Mono<UserWithItem> fetchUserAndItem(int userId, int itemId) {
        Mono<User> user = getUser(userId);
        Mono<Item> item = getItem(itemId);

        return Mono.zip(user, item, UserWithItem::new);
    }
}
