package com.baeldung.hexagonal.architecture.port;

import com.baeldung.hexagonal.architecture.domain.dto.Account;

public interface Persistor {

    public boolean persist(Account account);
}
