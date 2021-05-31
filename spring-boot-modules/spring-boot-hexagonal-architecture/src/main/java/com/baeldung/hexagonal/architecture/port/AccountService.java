package com.baeldung.hexagonal.architecture.port;

import java.util.List;

import com.baeldung.hexagonal.architecture.domain.model.Account;

/**
 * The interface is an inbound port provides the flow and the application functionality to the outside
 */
public interface AccountService {

    List<Account> getAccounts();

    Account getAccountById(Integer AccountId);

    Account addAccount(Account Account);

    Account removeAccount(Integer AccountId);
}
