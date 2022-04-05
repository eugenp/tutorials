package com.baeldung.compareto;

public class BankAccount implements Comparable<BankAccount> {

    private final int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    @Override
    public int compareTo(BankAccount anotherAccount) {
        return this.balance - anotherAccount.balance;
    }

}
