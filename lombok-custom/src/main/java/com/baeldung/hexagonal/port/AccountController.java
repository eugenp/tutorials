package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Account;

public interface AccountController {
    void createAccount(Account account);
}
