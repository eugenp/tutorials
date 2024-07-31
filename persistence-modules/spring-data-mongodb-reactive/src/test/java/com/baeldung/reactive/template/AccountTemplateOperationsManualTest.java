package com.baeldung.reactive.template;

import com.baeldung.reactive.Spring5ReactiveApplication;
import com.baeldung.reactive.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Spring5ReactiveApplication.class)
public class AccountTemplateOperationsManualTest {

    @Autowired
    AccountTemplateOperations accountTemplate;

    @Test
    public void givenAccount_whenSave_thenSave() {
        Account account = accountTemplate.save(Mono.just(new Account(null, "Raul", 12.3))).block();
        assertNotNull( account.getId() );
    }

    @Test
    public void givenId_whenFindById_thenFindAccount() {
        Mono<Account> accountMono = accountTemplate.save(Mono.just(new Account(null, "Raul", 12.3)));
        Mono<Account> accountMonoResult = accountTemplate.findById(accountMono.block().getId());
        assertNotNull(accountMonoResult.block().getId());
        assertEquals(accountMonoResult.block().getOwner(), "Raul");
    }

    @Test
    public void whenFindAll_thenFindAllAccounts() {
        Account account1 = accountTemplate.save(Mono.just(new Account(null, "Raul", 12.3))).block();
        Account account2 = accountTemplate.save(Mono.just(new Account(null, "Raul Torres", 13.3))).block();
        Flux<Account> accountFlux = accountTemplate.findAll();
        List<Account> accounts = accountFlux.collectList().block();
        assertTrue(accounts.stream().anyMatch(x -> account1.getId().equals(x.getId()) ));
        assertTrue(accounts.stream().anyMatch(x -> account2.getId().equals(x.getId()) ));
    }

}