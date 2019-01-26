package com.baeldung.tutorial.hexagonal.storage;

import com.baeldung.tutorial.hexagonal.domain.BankAccount;

public interface BankAccountRepository {

    BankAccount findByBankAccountId(String bankAccountId);

    void saveAccount(BankAccount bankAccount);
}
