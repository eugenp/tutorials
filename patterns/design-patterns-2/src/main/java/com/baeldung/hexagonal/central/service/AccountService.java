package com.baeldung.hexagonal.central.service;

import com.baeldung.hexagonal.central.domain.Account;
import com.baeldung.hexagonal.central.domain.AccountType;
import com.baeldung.hexagonal.central.repository.IAccountRepository;

public class AccountService implements IAccountService {

    private IAccountRepository accountRepository;

    public void setAccountRepository(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void createAccount(String name, String type) {

        Account account = new Account();
        account.setName(name);

        if ("user".equals(type)) {
            account.setType(AccountType.USER);
        }
        if ("mod".equals(type)) {
            account.setType(AccountType.MODERATOR);
        }

        accountRepository.save(account);

    }

}
