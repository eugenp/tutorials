package com.baeldung.hexagonal.ports.api;

import com.baeldung.hexagonal.domain.AccountDto;

public interface AccountServicePort {
    AccountDto addAccount(AccountDto account);
}
