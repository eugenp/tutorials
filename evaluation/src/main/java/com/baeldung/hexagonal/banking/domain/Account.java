package com.baeldung.hexagonal.banking.domain;

import java.math.BigDecimal;

public class Account {

    private AccountHolder accountHolder;
    private Long accountNumber;
    private int pin;
    private BigDecimal balance;

    protected Account(AccountHolder accountHolder, Long accountNumber, int pin, BigDecimal startingDeposit) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = startingDeposit;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public boolean validatePin(int attemptedPin) {
        return isGivenAttemptValid(attemptedPin) && sameAsAccountPin(attemptedPin);
    }

    private boolean isGivenAttemptValid(int attemptedPin) {
        return attemptedPin > 0;
    }

    private boolean sameAsAccountPin(int attemptedPin) {
        return this.pin == attemptedPin;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void creditAccount(BigDecimal amount) {
        if (isGivenAmountValid(amount))
            balance = balance.add(amount);
    }

    private boolean isGivenAmountValid(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ONE) >= 0;
    }

    public boolean debitAccount(BigDecimal amount) {
        if (isGivenAmountValid(amount) && isThereFunds(amount)) {
            balance = balance.subtract(amount);
            return true;
        } else {
            return false;
        }
    }

    private boolean isThereFunds(BigDecimal amount) {
        return amount.compareTo(this.balance) <= 0;
    }

}
