package com.baeldung.hexagonal.application.service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.application.Depositable;
import com.baeldung.hexagonal.application.Withdrawable;
import com.baeldung.hexagonal.application.repository.BankAccountRepository;
import com.baeldung.hexagonal.domain.BankAccount;

@Service
public class BankAccountService implements Depositable, Withdrawable {

    private BankAccountRepository repository;

    public BankAccountService(BankAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deposit(Long id, BigDecimal amount) {
        BankAccount account = repository.load(id)
            .orElseThrow(NoSuchElementException::new);

        account.deposit(amount);

        repository.save(account);
    }

    @Override
    public boolean withdraw(Long id, BigDecimal amount) {
        BankAccount account = repository.load(id)
            .orElseThrow(NoSuchElementException::new);

        boolean hasWithdrawn = account.withdraw(amount);

        if (hasWithdrawn) {
            repository.save(account);
        }
        
        return hasWithdrawn;
    }
}