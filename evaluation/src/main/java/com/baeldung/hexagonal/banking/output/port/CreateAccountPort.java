package com.baeldung.hexagonal.banking.output.port;

import com.baeldung.hexagonal.banking.domain.Account;

public interface CreateAccountPort {
    
    Account createAccount(Account account);

}
