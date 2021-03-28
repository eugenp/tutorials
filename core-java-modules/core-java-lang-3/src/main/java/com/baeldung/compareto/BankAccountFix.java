package com.baeldung.compareto;

public class BankAccountFix implements Comparable<BankAccountFix> {

    private final int balance;

    public BankAccountFix(int balance) {
        this.balance = balance;
    }

    @Override
    public int compareTo(BankAccountFix anotherAccount) {
        return Integer.compare(this.balance, anotherAccount.balance);
    }

}
