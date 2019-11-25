package com.baeldung.hexagonal_architecture.entity;

public class Account {

    private int id;
    private String accountHolderName;
    private int balance;

    public Account(int id, String accountHolderName, int balance) {
        super();
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
