package com.baeldung.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account implements Cloneable{
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Copy constructor for deep copying
    public Account(Account account) {
        this(account.getAccountNumber(), account.getBalance());
    }

    @Override
    public Account clone() {
        try {
            return (Account) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Account(this.getAccountNumber(), this.getBalance());
        }
    }
}
