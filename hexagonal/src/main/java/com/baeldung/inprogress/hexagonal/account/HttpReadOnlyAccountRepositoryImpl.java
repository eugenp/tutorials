package com.baeldung.inprogress.hexagonal.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Repository
@Profile("http")
public class HttpReadOnlyAccountRepositoryImpl implements ReadOnlyAccountRepository {

    private static final Logger logger = LoggerFactory.getLogger(HttpReadOnlyAccountRepositoryImpl.class);

    private String endpoint = "http://localhost:8080";

    @Override
    public Optional<Account> getAccountById(String id) {
        logger.info("Getting account via HTTP Adapter");
        WebClient webClient = WebClient.create(endpoint).mutate().build();
        try {
            Account account = webClient.get()
                    .uri("/accounts/{id}", id)
                    .retrieve()
                    .bodyToMono(Account.class).block();
            return Optional.of(account);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
