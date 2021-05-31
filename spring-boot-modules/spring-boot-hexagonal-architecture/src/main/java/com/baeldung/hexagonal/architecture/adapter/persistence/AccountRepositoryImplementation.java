package com.baeldung.hexagonal.architecture.adapter.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.architecture.domain.model.Account;
import com.baeldung.hexagonal.architecture.port.AccountRepository;

/**
 * The interface defines an output adapter which is an implementation of the outbound port. It enables the core application to communicate to external dependency such as the database.
 */
@Repository
public class AccountRepositoryImplementation implements AccountRepository {

    private static final Map<Integer, Account> AccountMap = new HashMap<Integer, Account>(0);

    @Override
    public List<Account> getAccounts() {
        return new ArrayList<Account>(AccountMap.values());
    }

    @Override
    public Account getAccountById(Integer AccountId) {
        return AccountMap.get(AccountId);
    }

    @Override
    public Account addAccount(Account Account) {
        AccountMap.put(Account.getAccountId(), Account);
        return Account;
    }

    @Override
    public Account removeAccount(Integer AccountId) {
        if (AccountMap.get(AccountId) != null) {
            Account Account = AccountMap.get(AccountId);
            AccountMap.remove(AccountId);
            return Account;
        } else
            return null;

    }
}
