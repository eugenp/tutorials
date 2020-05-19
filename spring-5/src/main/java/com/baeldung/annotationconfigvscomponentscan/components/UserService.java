package com.baeldung.annotationconfigvscomponentscan.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private AccountService accountService;

    public AccountService getAccountService() {
        return accountService;
    }

}
