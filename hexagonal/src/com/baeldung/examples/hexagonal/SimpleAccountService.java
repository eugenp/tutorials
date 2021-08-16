package com.baeldung.examples.hexagonal;

import java.util.UUID;

public class SimpleAccountService implements AccountService {
    private final AccountRepository accountRepository;

    public SimpleAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UUID createAccount() {
        Account account = new Account(UUID.randomUUID(), 0D);
        Account savedAccount = accountRepository.save(account);
        return savedAccount.getId();
    }

    @Override
    public Double deposit(UUID id, Double amount) {
        Account account = getAccount(id);
        Double deposit = account.deposit(amount);
        accountRepository.save(account);
        return deposit;
    }

    @Override
    public Double withdrawal(UUID id, Double amount) {
        Account account = getAccount(id);
        Double withdrawal = account.withdrawal(amount);
        accountRepository.save(account);
        return withdrawal;
    }

    @Override
    public Double getBalance(UUID id) {
        Account account = getAccount(id);
        return account.getBalance();
    }

    private Account getAccount(UUID id) {
        return accountRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
