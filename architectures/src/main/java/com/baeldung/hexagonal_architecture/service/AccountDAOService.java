package com.baeldung.hexagonal_architecture.service;

import com.baeldung.hexagonal_architecture.entity.Account;

public interface AccountDAOService {

    Account getAccount(int accountId);

    boolean updateAccountBalance(int accountId, int balance);
}
