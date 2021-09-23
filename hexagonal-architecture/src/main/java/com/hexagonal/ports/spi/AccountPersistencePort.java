package com.hexagonal.ports.spi;

import com.hexagonal.domain.AccountDto;

public interface AccountPersistencePort {
    AccountDto addAccount(AccountDto account);
}
