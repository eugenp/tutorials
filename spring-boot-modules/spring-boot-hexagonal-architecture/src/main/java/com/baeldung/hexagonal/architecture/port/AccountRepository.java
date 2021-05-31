package com.baeldung.hexagonal.architecture.port;

import java.util.List;

import com.baeldung.hexagonal.architecture.domain.model.Account;

/**
 * The repository interface is an outbound port that enables communication from the core application to a database
 */
public interface AccountRepository {

    List<Account> getAccounts();

    Account getAccountById(Integer AccountId);

    Account addAccount(Account Account);

    Account removeAccount(Integer AccountId);
}
