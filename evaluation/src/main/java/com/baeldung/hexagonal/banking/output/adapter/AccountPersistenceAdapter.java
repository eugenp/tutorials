package com.baeldung.hexagonal.banking.output.adapter;

import com.baeldung.hexagonal.banking.domain.Account;
import com.baeldung.hexagonal.banking.output.port.CreateAccountPort;
import com.baeldung.hexagonal.banking.output.port.LoadAccountPort;
import com.baeldung.hexagonal.banking.output.port.UpdateAccountPort;

public class AccountPersistenceAdapter implements CreateAccountPort, LoadAccountPort, UpdateAccountPort {

    @Override
    public Account updateAccount(Account account) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account loadAccount(Long accountNumber) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account createAccount(Account account) {
        // TODO Auto-generated method stub
        return null;
    }

}
