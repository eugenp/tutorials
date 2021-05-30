package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.Account;
import com.baeldung.hexagonal.port.AccountRepository;

public class AccountService {
    private AccountRepository accountRepository;
    public void createAccount(Account account) {
        accountRepository.createAccount(account);
    }
}
