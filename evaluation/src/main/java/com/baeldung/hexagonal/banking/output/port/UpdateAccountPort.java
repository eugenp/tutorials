package com.baeldung.hexagonal.banking.output.port;

import com.baeldung.hexagonal.banking.domain.Account;

public interface UpdateAccountPort {
    
    Account updateAccount(Account account);

}
