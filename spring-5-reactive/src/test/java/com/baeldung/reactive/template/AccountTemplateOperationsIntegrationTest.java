package com.baeldung.reactive.template;

import com.baeldung.reactive.Spring5ReactiveApplication;
import com.baeldung.reactive.model.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Spring5ReactiveApplication.class)
class AccountTemplateOperationsIntegrationTest {

    @Autowired
    AccountTemplateOperations accountTemplate;

    @AfterEach
    void cleanup() {
        accountTemplate.deleteAll();
    }

    @Test
    void shouldSave_GivenAccount() {
        Account account = accountTemplate.save(Mono.just(new Account(null, "bruno", 12.3))).block();
        assertNotNull( account.getId() );
    }

    @Test
    void shouldFind_GivenId() {
        Mono<Account> accountMono = accountTemplate.save(Mono.just(new Account(null, "bruno", 12.3)));
        Mono<Account> accountMonoResult = accountTemplate.findById(accountMono.block().getId());
        assertNotNull(accountMonoResult.block().getId());
        assertEquals(accountMonoResult.block().getOwner(), "bruno");
    }

    @Test
    void shouldFindAll() {
        accountTemplate.save(Mono.just(new Account(null, "bruno", 12.3))).block();
        accountTemplate.save(Mono.just(new Account(null, "bruno torrao", 13.3))).block();
        Flux<Account> accountFlux = accountTemplate.findAll();
        List<Account> accounts = accountFlux.collectList().block();
        assertEquals(accounts.size(), 2);
    }



}