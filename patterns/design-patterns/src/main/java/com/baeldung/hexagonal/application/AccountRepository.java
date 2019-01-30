package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.Account;

import java.util.Optional;

public interface AccountRepository {
    void save(Account account);
    Optional<Account> getAccount(String accountNumber);
}
