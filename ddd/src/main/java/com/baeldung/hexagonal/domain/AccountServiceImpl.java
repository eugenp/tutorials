package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.ports.api.AccountServicePort;
import com.baeldung.hexagonal.ports.spi.AccountPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountServiceImpl implements AccountServicePort {

    @Autowired
    AccountPersistencePort accountPersistencePort;

    public AccountServiceImpl(AccountPersistencePort accountPersistencePort) {
        this.accountPersistencePort = accountPersistencePort;
    }

    @Override
    public AccountDto addAccount(AccountDto accountDto) {
        return accountPersistencePort.addAccount(accountDto);
    }
}
