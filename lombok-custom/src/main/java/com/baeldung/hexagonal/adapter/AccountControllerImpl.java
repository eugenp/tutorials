package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.Account;
import com.baeldung.hexagonal.service.AccountService;

public class AccountControllerImpl {

    private AccountService accountService;

    public void createAccount(Account account) {
        accountService.createAccount(account);
    }
}
