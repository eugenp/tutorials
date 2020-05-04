package com.baeldung.hexagonal.banking.output.adapter;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.baeldung.hexagonal.banking.domain.Account;
import com.baeldung.hexagonal.banking.domain.CommercialAccount;
import com.baeldung.hexagonal.banking.domain.Company;

@DataJpaTest
@Import({ AccountPersistenceAdapter.class })
class AccountPersistenceAdapterIntegrationTest {

    @Autowired
    private AccountPersistenceAdapter target;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Sql("AccountAdapterTestData.sql")
    void loadsAccount_whenAccountExists() {
        Account account = target.loadAccount(1L).get();

        assertThat(account.getAccountHolder()
            .getIdNumber()).isEqualTo(156789);
        assertThat(account.getBalance()
            .compareTo(BigDecimal.ZERO)).isEqualTo(0);
    }

    @Test
    @Sql("AccountAdapterTestData.sql")
    void throwException_whenAccountDoesntExist() {

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            target.loadAccount(10L);
        });

    }

    @Test
    void mustCreateNewAccount_whenAccountDoesntExist() {

        Account inexistentAccountStub = givenANewAccount();

        whenPersisted(inexistentAccountStub);

        thenNewAccountIsCreatedWithInitialBalance(inexistentAccountStub);
    }

    @Test
    @Sql("AccountAdapterTestData.sql")
    void mustUpdateAccount_whenAccountExists() {

        Account existentAccount = givenExistentAccountBeingCredited10();

        whenPersisted(existentAccount);

        thenBalanceOfSameAccountIsIncreasedBy10(existentAccount);

    }

    private Account givenANewAccount() {
        Account inexistentAccountStub = new CommercialAccount(new Company("Inexistent S.A", 123456), 1234, BigDecimal.TEN);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            target.loadAccount(inexistentAccountStub.getAccountNumber());
        });
        return inexistentAccountStub;
    }

    private void thenNewAccountIsCreatedWithInitialBalance(Account inexistentAccountStub) {
        assertThat(accountRepository.count()).isEqualTo(1);

        AccountJpaEntity actualSavedAccount = accountRepository.findAll()
            .get(0);
        assertThat(actualSavedAccount.getAccountNumber()).isEqualTo(inexistentAccountStub.getAccountNumber());
        assertThat(actualSavedAccount.getBalance()).isEqualTo(BigDecimal.TEN);
    }

    private void thenBalanceOfSameAccountIsIncreasedBy10(Account existentAccount) {
        AccountJpaEntity actualSavedAccount = accountRepository.findById(2L)
            .get();
        assertThat(actualSavedAccount.getAccountNumber()).isEqualTo(existentAccount.getAccountNumber());
        assertThat(actualSavedAccount.getBalance()
            .compareTo(BigDecimal.valueOf(1010D))).isEqualTo(0);
    }

    private void whenPersisted(Account existentAccount) {
        target.createOrUpdateAccount(existentAccount);
    }

    private Account givenExistentAccountBeingCredited10() {
        Account existentAccount = target.loadAccount(2L).get();
        assertThat(existentAccount.getAccountHolder()
            .getIdNumber()).isEqualTo(98765);
        assertThat(existentAccount.getBalance()
            .compareTo(BigDecimal.valueOf(1000))).isEqualTo(0);

        existentAccount.creditAccount(BigDecimal.TEN);
        return existentAccount;
    }

}
