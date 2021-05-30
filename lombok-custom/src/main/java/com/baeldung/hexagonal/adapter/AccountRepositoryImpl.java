package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.Account;
import java.util.*;

public class AccountRepositoryImpl {
    private Map<Long, Account> accountDB = new HashMap<>();
    public void createAccount(Account account) {
        accountDB.put(account.getAccountNumber(), account);
    }
}
