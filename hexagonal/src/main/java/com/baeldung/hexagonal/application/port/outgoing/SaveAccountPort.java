package com.baeldung.hexagonal.application.port.outgoing;

import com.baeldung.hexagonal.application.domain.BankAccount;

public interface SaveAccountPort {
    void save(BankAccount bankAccount);
}