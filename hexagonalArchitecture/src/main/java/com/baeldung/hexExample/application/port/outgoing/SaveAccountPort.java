package com.baeldung.hexExample.application.port.outgoing;

import com.baeldung.hexExample.application.domain.BankAccount;

public interface SaveAccountPort {
    void save(BankAccount bankAccount);
}