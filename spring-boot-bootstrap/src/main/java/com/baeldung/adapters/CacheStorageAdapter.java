package com.baeldung.adapters;
/*
 * created by pareshP on 02/04/19
 */

import com.baeldung.persistence.model.Account;
import com.baeldung.ports.AccountOperationsPort;
import com.baeldung.web.exception.AccountNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class CacheStorageAdapter implements AccountOperationsPort {

    private static Map<String, Account> accountCache = new HashMap<>();


    @Override
    public Account createAccount(Account account) {
        if (null != account && StringUtils.hasText(account.getAccountId())) {
            accountCache.put(account.getAccountId(), account);
            return accountCache.get(account.getAccountId());
        }
        return null;
    }

    @Override
    public Account returnAccount(String accountId) {
        if(accountCache.containsKey(accountId)) {
            return accountCache.get(accountId);
        } else {
            throw new AccountNotFoundException("Account Not Found for id: " + accountId);
        }
    }

    @Override
    public boolean deleteAccount(String accountId) {
        return null != accountCache.remove(accountId);
    }

    @Override
    public Account updateAccount(Account account) {
        if (null != account && StringUtils.hasText(account.getAccountId())) {
            if (accountCache.containsKey(account.getAccountId())) {
                return accountCache.put(account.getAccountId(), account);
            } else {
                throw new AccountNotFoundException("Account Not Found for id: " + account.getAccountId());
            }
        }
        return null;
    }
}
