package com.baeldung.services;

import com.baeldung.interfaces.IAccountService;

import com.baeldung.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepositoryImpl accountRepository;

    @Override
    public void createAccount(Account account) {
        accountRepository.createAccount(account);
    }

    @Override
    public Account getAccount(Long accountNumber) {
        return accountRepository.getAccount(accountNumber);
    }

    @Override
    public List allAccounts() {
        return accountRepository.allAccounts();
    }
}