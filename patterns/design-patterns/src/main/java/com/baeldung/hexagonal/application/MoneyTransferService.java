package com.baeldung.hexagonal.application;

public class MoneyTransferService {

    private final AccountRepository accountRepository;

    public MoneyTransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void sendMoney(MoneyTransferInput moneyTransferInput) {
        accountRepository.getAccount(moneyTransferInput.getAccountNumber())
                .ifPresent(account -> account.applyTransaction(moneyTransferInput.getTransaction()));
    }

}
