package com.baeldung.ports;
/*
 * created by pareshP on 02/04/19
 */

import com.baeldung.persistence.model.Account;

public interface AccountOperationsPort {
    Account createAccount(Account account);
    Account returnAccount(String accountId);
    Account updateAccount(Account account);
    boolean deleteAccount(String accountId);
}
