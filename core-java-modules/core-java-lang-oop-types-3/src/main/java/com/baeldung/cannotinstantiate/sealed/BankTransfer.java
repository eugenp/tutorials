package com.baeldung.cannotinstantiate.sealed;

public final class BankTransfer extends Payment {
    private final String accountNumber;

    public BankTransfer(double amount) {
        this.amount = amount;
        this.accountNumber = "";
    }

    public BankTransfer(double amount, String accountNumber) {
        this.amount = amount;
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
