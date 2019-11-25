package com.baeldung.hexagonal_architecture.service_impl;

import static com.baeldung.hexagonal_architecture.data.AccountsData.accountsData;

import com.baeldung.hexagonal_architecture.entity.Account;
import com.baeldung.hexagonal_architecture.service.AccountDAOService;

public class AccountDAOServiceImpl implements AccountDAOService {

    public Account getAccount(int accountId) {
        return accountsData.stream()
            .filter(account -> account.getId() == accountId)
            .findFirst()
            .get();
    }

    public boolean updateAccountBalance(int accountId, int balance) {
        try {
            Account accountInformation = accountsData.stream()
                .filter(account -> account.getId() == accountId)
                .findFirst()
                .get();

            accountInformation.setBalance(balance);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
