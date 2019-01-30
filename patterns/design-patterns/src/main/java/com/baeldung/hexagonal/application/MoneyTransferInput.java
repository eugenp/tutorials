package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.Transaction;

public interface MoneyTransferInput {
    String getAccountNumber();
    Transaction getTransaction();
}
