package com.baeldung.webclientretrievevsexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RetrieveAndExchangeController {

    private static final Logger logger = LoggerFactory.getLogger(RetrieveAndExchangeController.class);

    WebClient client = WebClient.create("https://jsonplaceholder.typicode.com/users");

    @GetMapping("/user/{id}")
    Mono<User> retrieveOneUser(@PathVariable int id) {
        return client.get()
            .uri("/{id}", id)
            .retrieve()
            .bodyToMono(User.class)
            .onErrorResume(Mono::error);
    }

    @GetMapping("/user-status/{id}")
    Mono<User> retrieveOneUserAndHandleErrorBasedOnStatus(@PathVariable int id) {
        return client.get()
            .uri("/{id}", id)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Client Error: can't fetch user")))
            .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Server Error: can't fetch user")))
            .bodyToMono(User.class);
    }

    @GetMapping("/user-id/{id}")
    Mono<ResponseEntity<User>> retrieveOneUserWithResponseEntity(@PathVariable int id) {
        return client.get()
            .uri("/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(User.class)
            .onErrorResume(Mono::error);
    }

    @GetMapping("/users")
    Flux<User> retrieveAllUsers() {
        return client.get()
            .retrieve()
            .bodyToFlux(User.class)
            .onErrorResume(Flux::error);
    }

    @GetMapping("/user/exchange-alter/{id}")
    Mono<User> retrieveOneUserWithExchangeAndManipulate(@PathVariable int id) {
        return client.get()
            .uri("/{id}", id)
            .exchangeToMono(res -> res.bodyToMono(User.class))
            .map(user -> {
                user.setName(user.getName()
                    .toUpperCase());
                user.setId(user.getId() + 100);
                return user;
            });
    }

    @GetMapping("/user/exchange-mono/{id}")
    Mono<User> retrieveUsersWithExchangeAndError(@PathVariable int id) {
        return client.get()
            .uri("/{id}", id)
            .exchangeToMono(res -> {
                if (res.statusCode()
                    .is2xxSuccessful()) {
                    return res.bodyToMono(User.class);
                } else if (res.statusCode()
                    .is4xxClientError()) {
                    return Mono.error(new RuntimeException("Client Error: can't fetch user"));
                } else if (res.statusCode()
                    .is5xxServerError()) {
                    return Mono.error(new RuntimeException("Server Error: can't fetch user"));
                } else {
                    return res.createError();
                }
            });
    }

    @GetMapping("/user/exchange-header/{id}")
    Mono<User> retrieveUsersWithExchangeAndHeader(@PathVariable int id) {
        return client.get()
            .uri("/{id}", id)
            .exchangeToMono(res -> {
                if (res.statusCode()
                    .is2xxSuccessful()) {
                    logger.info("Status code: " + res.headers()
                        .asHttpHeaders());
                    logger.info("Content-type" + res.headers()
                        .contentType());
                    return res.bodyToMono(User.class);
                } else if (res.statusCode()
                    .is4xxClientError()) {
                    return Mono.error(new RuntimeException("Client Error: can't fetch user"));
                } else if (res.statusCode()
                    .is5xxServerError()) {
                    return Mono.error(new RuntimeException("Server Error: can't fetch user"));
                } else {
                    return res.createError();
                }
            });
    }

    @GetMapping("/user-exchange")
    Flux<User> retrieveAllUserWithExchange(@PathVariable int id) {
        return client.get()
            .exchangeToFlux(res -> res.bodyToFlux(User.class))
            .onErrorResume(Flux::error);
    }

    @GetMapping("/user-exchange-flux")
    Flux<User> retrieveUsersWithExchange() {
        return client.get()
            .exchangeToFlux(res -> {
                if (res.statusCode()
                    .is2xxSuccessful()) {
                    return res.bodyToFlux(User.class);
                } else {
                    return Flux.error(new RuntimeException("Error while fetching users"));
                }
            });
    }
}
