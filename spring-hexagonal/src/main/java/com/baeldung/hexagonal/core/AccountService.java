package com.baeldung.hexagonal.core;

import com.baeldung.hexagonal.model.Account;
import com.baeldung.hexagonal.model.AccountNotFoundException;
import com.baeldung.hexagonal.ports.entry.AccountPorts;
import com.baeldung.hexagonal.ports.exit.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements AccountPorts {
    private AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account openAccount(BigDecimal initialDeposit) {
        if (BigDecimal.ZERO.compareTo(initialDeposit) > 0) {
            throw new IllegalArgumentException("Initial deposits must be greater than zero!");
        }
        Account account = new Account(initialDeposit);
        repository.save(account);
        return account;
    }

    public Account getAccount(Long id) throws AccountNotFoundException {
        return repository
          .findById(id)
          .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public BigDecimal depositMoney(Long id, BigDecimal amount) throws AccountNotFoundException {
        Optional<Account> account = repository.findById(id);
        account.ifPresent(acct -> {
            acct.setBalance(acct
              .getBalance()
              .add(amount));
            repository.save(acct);
        });
        return account
          .map(Account::getBalance)
          .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public BigDecimal checkBalance(Long id) throws AccountNotFoundException {
        return repository
          .findById(id)
          .map(Account::getBalance)
          .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public List<Account> searchByBalanceMoreThan(BigDecimal moreThanBalance) {
        return repository.findAccountsByBalanceGreaterThan(moreThanBalance);
    }

}
