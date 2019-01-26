package com.baeldung.tutorial.hexagonal.application;

import com.baeldung.tutorial.hexagonal.domain.BankAccount;
import com.baeldung.tutorial.hexagonal.storage.BankAccountRepository;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepository repository;

    BankAccountServiceImpl(BankAccountRepository repository) {
        this.repository = repository;
    }

    public void withdraw(String bankAccountId, BigDecimal withdrawalAmount) {
        BankAccount bankAccount = repository.findByBankAccountId(bankAccountId);

        if (bankAccount == null) {
            throw new IllegalArgumentException(String.format("No bank account found with accountId %s", bankAccountId));
        }

        BigDecimal currentAccountBalance = bankAccount.getAccountBalance();
        BigDecimal balanceAfterWithdrawal = currentAccountBalance.subtract(withdrawalAmount);
        if (currentAccountBalance.subtract(withdrawalAmount)
            .compareTo(ZERO) > 0) {
            bankAccount.setAccountBalance(balanceAfterWithdrawal);
            repository.saveAccount(bankAccount);
        } else {
            throw new IllegalArgumentException(String.format("Balance %d not enough to withdraw %d", currentAccountBalance, withdrawalAmount));
        }
    }
}
