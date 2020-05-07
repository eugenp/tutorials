package com.baeldung.hexagonal.banking.domain;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Account {

    private AccountHolder accountHolder;
    private Long accountNumber;
    private int pin;
    private BigDecimal balance;

    protected Account(AccountHolder accountHolder, int pin, BigDecimal startingDeposit) {
        this.accountHolder = accountHolder;
        this.pin = pin;
        this.balance = startingDeposit;
        this.accountNumber = getNextAccountNumber();
    }
    
    protected Account(Long accountNumber, AccountHolder accountHolder, int pin, BigDecimal startingDeposit) {
        this.accountHolder = accountHolder;
        this.pin = pin;
        this.balance = startingDeposit;
        this.accountNumber = accountNumber;
    }
    
    private Long getNextAccountNumber() {
        return UUID.randomUUID()
            .getMostSignificantBits();

    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }
    
    public int getPin() {
        return pin;
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
        if (isGivenAmountValid(amount) && areThereFunds(amount)) {
            balance = balance.subtract(amount);
            return true;
        } else {
            return false;
        }
    }

    private boolean areThereFunds(BigDecimal amount) {
        return amount.compareTo(this.balance) <= 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (accountNumber == null) {
            if (other.accountNumber != null)
                return false;
        } else if (!accountNumber.equals(other.accountNumber))
            return false;
        return true;
    }

}
