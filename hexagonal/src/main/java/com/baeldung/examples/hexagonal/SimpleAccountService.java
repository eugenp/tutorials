package main.java.com.baeldung.examples.hexagonal;

import java.util.UUID;

public class SimpleAccountService implements AccountService {
    private final AccountRepository accountRepository;

    public SimpleAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UUID createAccount() {
        var account = new Account(UUID.randomUUID(), 0D);
        var savedAccount = accountRepository.save(account);
        return savedAccount.getId();
    }

    @Override
    public Double deposit(UUID id, Double amount) {
        var account = get(id);
        var deposit = account.deposit(amount);
        accountRepository.save(account);
        return deposit;
    }

    @Override
    public Double withdrawal(UUID id, Double amount) {
        var account = get(id);
        var withdrawal = account.withdrawal(amount);
        accountRepository.save(account);
        return withdrawal;
    }

    @Override
    public Double getBalance(UUID id) {
        var account = get(id);
        return account.getBalance();
    }

    private Account get(UUID id) {
        return accountRepository.findById(id).orElseThrow(RuntimeException::new);
    }


}
