package com.baeldung.infrastructure.adapter;

import org.springframework.stereotype.Component;

import com.baeldung.domain.model.Account;
import com.baeldung.domain.port.outgoing.RetrieveAccount;
import com.baeldung.domain.port.outgoing.PersistAccount;

@Component
public class AccountRepository implements RetrieveAccount, PersistAccount {

    private SpringDataAccountRepository repository;

    public AccountRepository(SpringDataAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account load(Long accountNo) {
        return repository.findByAccountNo(accountNo);
    }

    @Override
    public void save(Account bankAccount) {
        repository.save(bankAccount);
    }
}
