package com.baeldung.reactive.repository;

import com.baeldung.reactive.Spring5ReactiveApplication;
import com.baeldung.reactive.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

        StepVerifier
                .create(accountFlux)
                .assertNext(account -> assertEquals("john", account.getOwner()))
                .expectComplete()
                .verify();
    }

    @Test
    public void givenAccount_whenSave_thenSave() {
        Mono<Account> accountMono = repository.save(new Account(null, "john", 12.3));

        StepVerifier
                .create(accountMono)
                .assertNext(account -> assertNotNull(account.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    public void givenId_whenFindById_thenFindAccount() {
        Account inserted = repository.save(new Account(null, "john", 12.3)).block();
        Mono<Account> accountMono = repository.findById(inserted.getId());

        StepVerifier
                .create(accountMono)
                .assertNext(account -> {
                    assertEquals("john", account.getOwner());
                    assertEquals(Double.valueOf(12.3),  account.getValue());
                    assertNotNull(account.getId());
                })
                .expectComplete()
                .verify();
    }
}