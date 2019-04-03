package com.baeldung.adapters;
/*
 * created by pareshP on 02/04/19
 */

import com.baeldung.persistence.model.Account;
import com.baeldung.persistence.repo.AccountRepository;
import com.baeldung.ports.AccountOperationsPort;
import com.baeldung.web.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PersistentStorageAdapter implements AccountOperationsPort {

    private AccountRepository accountRepository;

    public PersistentStorageAdapter(@Autowired AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Account account) {
        if (null != account) {
            return accountRepository.save(account);
        }
        return null;
    }

    @Override
    public Account returnAccount(String accountId) {
        if (StringUtils.hasText(accountId)) {
            return accountRepository.findById(accountId)
              .orElseThrow(AccountNotFoundException::new);
        }
        return null;
    }

    @Override
    public boolean deleteAccount(String accountId) {
        if (StringUtils.hasText(accountId)) {
            accountRepository.deleteById(accountId);
        }
        return true;
    }

    @Override
    public Account updateAccount(Account account) {
        if (null != account && StringUtils.hasText(account.getAccountId())) {
            accountRepository.findById(account.getAccountId())
              .orElseThrow(AccountNotFoundException::new);
            return accountRepository.save(account);
        }
        return null;
    }
}
