package com.baeldung.services;

import com.baeldung.interfaces.IAccountService;

import com.baeldung.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService {

   @Autowired
    private IAccountRepository accountRepository;

    public AccountService() {
    }

    public AccountService(AccountRepositoryImpl accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Account account) {
        accountRepository.createAccount(account);
        return account;
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
