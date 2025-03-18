package com.baeldung.namingconventions;

class CustomerAccount {
    private String accountNumber;
    private double balance;

    public static final double MAX_BALANCE = 1000000.00;

    public void deposit(double amount) {
        if (balance + amount > MAX_BALANCE) {
            System.out.println("Deposit exceeds max balance limit.");
        } else {
            this.balance += amount;
        }
    }
}

