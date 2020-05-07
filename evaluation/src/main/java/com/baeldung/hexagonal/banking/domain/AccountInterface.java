package com.baeldung.hexagonal.banking.domain;

public interface AccountInterface {

    public AccountHolder getAccountHolder();

    public boolean validatePin(int attemptedPin);

    public double getBalance();

    public Long getAccountNumber();

    public void creditAccount(double amount);

    public boolean debitAccount(double amount);
}
