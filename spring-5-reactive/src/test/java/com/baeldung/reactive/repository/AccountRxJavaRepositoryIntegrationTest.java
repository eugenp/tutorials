package com.baeldung.reactive.repository;

import com.baeldung.reactive.Spring5ReactiveApplication;
import com.baeldung.reactive.model.Account;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Spring5ReactiveApplication.class)
public class AccountRxJavaRepositoryIntegrationTest {

    @Autowired
    AccountRxJavaRepository repository;

    @Before
    public void setUp() {
        repository.save(new Account(null, "bruno", 12.3)).blockingGet();
    }

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void givenValue_WhenFindAllByValue_ShouldFindAccounts() {
        Observable<Account> accountObservable = repository.findAllByValue(12.3);
        Account account = accountObservable.blockingFirst();
        assertEquals("bruno", account.getOwner());
        assertEquals(Double.valueOf(12.3), account.getValue());
        assertNotNull(account.getId());
    }

    @Test
    public void givenOwner_WhenFindFirstByOwner_ShouldFindAccount() {
        Single<Account> accountSingle = repository.findFirstByOwner(Single.just("bruno"));
        Account account = accountSingle.blockingGet();
        assertEquals("bruno", account.getOwner());
        assertEquals(Double.valueOf(12.3), account.getValue());
        assertNotNull(account.getId());
    }

}