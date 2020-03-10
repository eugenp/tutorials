package com.baeldung.hexagonal.application.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.domain.BankAccount;

@Component
public class InMemoryBankAccountRepository implements BankAccountRepository {
    
    private Map<Long, BankAccount> accounts = new HashMap<Long, BankAccount>();

    @Override
    public void save(BankAccount bankAccount) {
        accounts.put(bankAccount.getId(), bankAccount);
    }

    @Override
    public Optional<BankAccount> load(Long id) {
        return Optional.ofNullable(accounts.get(id));
    }
}