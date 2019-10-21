package com.baeldung.hexagonal.model;

public class AccountNotFoundException extends Exception {
    Long accountId;

    public AccountNotFoundException(Long id) {
        accountId = id;
    }

    @Override
    public String toString() {
        return "Account not found: " + accountId;
    }
}
