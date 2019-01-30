package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.application.AccountRepository;
import com.baeldung.hexagonal.domain.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {

    private final List<Account> accounts = new ArrayList<>();

    @Override
    public void save(Account account) {
        accounts.add(account);
    }

    @Override
    public Optional<Account> getAccount(String accountNumber) {
        return accounts.stream()
                .filter(account -> Objects.equals(account.getAccountNumber(), accountNumber))
                .findFirst();
    }

}
