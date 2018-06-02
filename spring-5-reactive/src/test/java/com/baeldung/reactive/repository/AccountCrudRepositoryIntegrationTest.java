package com.baeldung.reactive.repository;


import com.baeldung.reactive.Spring5ReactiveApplication;
import com.baeldung.reactive.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Spring5ReactiveApplication.class)
public class AccountCrudRepositoryIntegrationTest {

    @Autowired
    AccountCrudRepository repository;

    @Test
    public void givenValue_whenFindAllByValue_thenFindAccount() {
        repository.save(new Account(null, "Bill", 12.3)).block();
        Flux<Account> accountFlux = repository.findAllByValue(12.3);
        Account account = accountFlux.next().block();
        assertEquals("Bill", account.getOwner());
        assertEquals(Double.valueOf(12.3) , account.getValue());
        assertNotNull(account.getId());
    }

    @Test
    public void givenOwner_whenFindFirstByOwner_thenFindAccount() {
        repository.save(new Account(null, "Bill", 12.3)).block();
        Mono<Account> accountMono = repository.findFirstByOwner(Mono.just("Bill"));
        Account account = accountMono.block();
        assertEquals("Bill", account.getOwner());
        assertEquals(Double.valueOf(12.3) , account.getValue());
        assertNotNull(account.getId());
    }

    @Test
    public void givenAccount_whenSave_thenSaveAccount() {
        Mono<Account> accountMono = repository.save(new Account(null, "Bill", 12.3));
        assertNotNull(accountMono.block().getId());
    }


}