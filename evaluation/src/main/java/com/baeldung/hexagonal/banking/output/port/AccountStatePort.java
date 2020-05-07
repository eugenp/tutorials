package com.baeldung.hexagonal.banking.output.port;

import java.util.Optional;

import com.baeldung.hexagonal.banking.domain.Account;

public interface AccountStatePort {
    
    Account createOrUpdateAccount(Account account);
    Optional<Account> loadAccount(Long accountNumber);

}
