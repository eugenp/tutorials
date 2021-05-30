package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Account;

public interface AccountRepository {
    void createAccount(Account account);
}
