package com.baeldung.hexagonal.ports.spi;

import com.baeldung.hexagonal.domain.AccountDto;

public interface AccountPersistencePort {
    AccountDto addAccount(AccountDto account);
}
