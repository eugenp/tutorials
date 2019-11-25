package com.baeldung.hexagonal_architecture.service_impl;

import com.baeldung.hexagonal_architecture.entity.Account;
import com.baeldung.hexagonal_architecture.service.AccountDAOService;
import com.baeldung.hexagonal_architecture.service.AccountService;

public class AccountServiceImpl implements AccountService {

    private AccountDAOService accountDaoService = new AccountDAOServiceImpl();

    public boolean withdrawAmount(int accountId, int amount) {
        try {
            final Account accountInformation = accountDaoService.getAccount(accountId);
            final int updatedBalance = accountInformation.getBalance() - amount;
            accountDaoService.updateAccountBalance(accountId, updatedBalance);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean depositAmount(int accountId, int amount) {
        try {
            final Account accountInformation = accountDaoService.getAccount(accountId);
            final int updatedBalance = accountInformation.getBalance() + amount;
            accountDaoService.updateAccountBalance(accountId, updatedBalance);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int getAccountBalance(int accountId) {
        return accountDaoService.getAccount(accountId)
            .getBalance();
    }
}
