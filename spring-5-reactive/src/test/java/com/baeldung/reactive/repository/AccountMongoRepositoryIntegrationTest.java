package com.baeldung.reactive.repository;

import com.baeldung.reactive.Spring5ReactiveApplication;
import com.baeldung.reactive.model.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Spring5ReactiveApplication.class)
public class AccountMongoRepositoryIntegrationTest {

    @Autowired
    AccountMongoRepository repository;

    @Test
    public void givenExample_whenFindAllWithExample_thenFindAllMacthings() {
        repository.save(new Account(null, "john", 12.3)).block();
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("owner", startsWith());
        Example<Account> example = Example.of(new Account(null, "jo", null), matcher);
        Flux<Account> accountFlux = repository.findAll(example);
        List<Account> accounts = accountFlux.collectList().block();

        assertTrue(accounts.stream().anyMatch(x -> x.getOwner().equals("john")));
    }

    @Test
    public void givenAccount_whenSave_thenSave() {
        Mono<Account> accountMono = repository.save(new Account(null, "john", 12.3));
        assertNotNull(accountMono.block().getId());
    }

    @Test
    public void givenId_whenFindById_thenFindAccount() {
        Account inserted = repository.save(new Account(null, "john", 12.3)).block();
        Mono<Account> accountMono = repository.findById(inserted.getId());
        assertEquals("john", accountMono.block().getOwner());
        assertEquals(Double.valueOf(12.3),  accountMono.block().getValue());
        assertNotNull(accountMono.block().getId());
    }
}