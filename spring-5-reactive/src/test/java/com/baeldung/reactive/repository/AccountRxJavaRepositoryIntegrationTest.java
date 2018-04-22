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
    public void shouldFindAccount_GivenValue() {
        Observable<Account> accountObservable = repository.findAllByValue(12.3);
        Account account = accountObservable.blockingFirst();
        assert account.getOwner().equals("bruno");
        assert account.getValue().equals(12.3);
        assertNotNull(account.getId());
    }

    @Test
    public void shouldFindAccount_GivenOwner() {
        Single<Account> accountSingle = repository.findFirstByOwner(Single.just("bruno"));
        Account account = accountSingle.blockingGet();
        assert account.getOwner().equals("bruno");
        assert account.getValue().equals(12.3);
        assertNotNull(account.getId());
    }

}