package com.baeldung.services;

import com.baeldung.interfaces.IAccountRepository;
import com.baeldung.model.Account;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class AccountRepositoryImpl implements IAccountRepository {

    private Map<Long, Account> accountDB = new HashMap<Long, Account>();
    
    @Override
    public Account createAccount(Account account) {
        accountDB.put(account.getAccountNumber(), account);
        return account;
    }


    @Override
    public Account getAccount(Long accountNumber) {
        return accountDB.get(accountNumber);
    }

    @Override
    public List allAccounts() {
        return accountDB.values().stream().collect(Collectors.toList());
    }
}
