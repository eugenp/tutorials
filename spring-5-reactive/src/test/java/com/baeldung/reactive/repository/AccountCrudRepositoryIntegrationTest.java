package com.baeldung.reactive.repository;


import com.baeldung.reactive.Spring5ReactiveApplication;
import com.baeldung.reactive.model.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Spring5ReactiveApplication.class)
public class AccountCrudRepositoryIntegrationTest {

    @Autowired
    AccountCrudRepository repository;

    @BeforeEach
    public void setUp() {
        repository.save(new Account(null, "bruno", 12.3)).block();
    }

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void shouldFindAccount_GivenValue() {
        Flux<Account> accountFlux = repository.findAllByValue(12.3);
        Account account = accountFlux.next().block();
        assert account.getOwner().equals("bruno");
        assert account.getValue().equals(12.3);
        assertNotNull(account.getId());
    }

    @Test
    public void shouldFindAccount_GivenOwner() {
        Mono<Account> accountMono = repository.findFirstByOwner(Mono.just("bruno"));
        Account account = accountMono.block();
        assert account.getOwner().equals("bruno");
        assert account.getValue().equals(12.3);
        assertNotNull(account.getId());
    }

    @Test
    public void shouldSave_GivenAnAccount() {
        Mono<Account> accountMono = repository.save(new Account(null, "bruno", 12.3));
        assertNotNull(accountMono.block().getId());
    }


}