package com.baeldung.hexagonal.architecture.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.domain.model.Account;
import com.baeldung.hexagonal.architecture.port.AccountRepository;
import com.baeldung.hexagonal.architecture.port.AccountService;

/**
 * The class is an use case implementation of the inbound port.
 */
@Service
public class AccountServiceImplementation implements AccountService {

    @Autowired
    private AccountRepository AccountRepository;

    @Override
    public List<Account> getAccounts() {
        return AccountRepository.getAccounts();
    }

    @Override
    public Account getAccountById(Integer AccountId) {
        return AccountRepository.getAccountById(AccountId);
    }

    @Override
    public Account addAccount(Account Account) {
        return AccountRepository.addAccount(Account);
    }

    @Override
    public Account removeAccount(Integer AccountId) {
        return AccountRepository.removeAccount(AccountId);
    }
}
