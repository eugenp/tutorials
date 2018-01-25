package com.baeldung.samplebeaninjectionypes;

import org.springframework.beans.factory.annotation.Autowired;

public class BankAccountWithConstructorInjection {
    private BankAccountService bankAccountService;

    @Autowired
    public BankAccountWithConstructorInjection(BankAccountService service) {
        this.bankAccountService = service;
    }

    public AccountDetails openAccount(Long accountNumber, String accountType, String owner) {
        return bankAccountService.openAccount(accountNumber, accountType, owner);
    }

}
