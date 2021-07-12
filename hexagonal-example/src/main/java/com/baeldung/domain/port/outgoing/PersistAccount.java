package com.baeldung.domain.port.outgoing;

import com.baeldung.domain.model.Account;

public interface PersistAccount {

    void save(Account account);
}