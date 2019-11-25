package com.baeldung.hexagonal_architecture.service;

public interface AccountService {

    boolean withdrawAmount(int accountId, int amount);

    boolean depositAmount(int accountId, int amount);

    int getAccountBalance(int accountId);
}
