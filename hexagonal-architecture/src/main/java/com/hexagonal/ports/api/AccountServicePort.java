package com.hexagonal.ports.api;

import com.hexagonal.domain.AccountDto;

public interface AccountServicePort {
    AccountDto addAccount(AccountDto account);
}
