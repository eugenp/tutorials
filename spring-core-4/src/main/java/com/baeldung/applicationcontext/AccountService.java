package com.baeldung.applicationcontext;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageSource messageSource;

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String getAccountName() {
        return messageSource.getMessage("account.name", null, Locale.ENGLISH);
    }

}
