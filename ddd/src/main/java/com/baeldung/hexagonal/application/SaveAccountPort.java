package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.BankAccount;

public interface SaveAccountPort {
    void save(BankAccount bankAccount);
}