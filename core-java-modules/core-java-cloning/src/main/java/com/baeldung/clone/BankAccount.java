package com.baeldung.clone;

class BankAccount implements Cloneable {
    private double balance;
    private TransactionHistory transactionHistory;

    public BankAccount(double balance, TransactionHistory transactionHistory) {
        this.balance = balance;
        this.transactionHistory = transactionHistory;
    }

    public void deposit(double amount) {
        this.balance += amount;
        this.transactionHistory.addTransaction(amount, "Deposit");
    }

    // Getter for balance
    public double getBalance() {
        return balance;
    }

    // Getter for transaction history
    public String getTransactionHistory() {
        return transactionHistory.getRecordOfAllTransactions();
    }

    // Creates a shallow copy of the BankAccount, sharing the same transaction history
    public BankAccount shallowClone() throws CloneNotSupportedException {
        return (BankAccount) super.clone();
    }

    // Creates a deep copy of the BankAccount, with a new transaction history
    @Override
    public BankAccount clone() throws CloneNotSupportedException {
        BankAccount cloned = (BankAccount) super.clone();
        cloned.transactionHistory = (TransactionHistory) this.transactionHistory.clone();
        return cloned;
    }

}

class TransactionHistory implements Cloneable {
    private StringBuilder recordOfAllTransactions;

    public TransactionHistory() {
        this.recordOfAllTransactions = new StringBuilder();
    }

    public void addTransaction(double amount, String type) {
        String transaction = String.format("%s: %.2f%n", type, amount);
        recordOfAllTransactions.append(transaction);
    }

    public String getRecordOfAllTransactions() {
        return recordOfAllTransactions.toString();
    }

    public void appendTransactions(String transactions) {
        recordOfAllTransactions.append(transactions);
    }

    @Override
    public TransactionHistory clone() throws CloneNotSupportedException {
        TransactionHistory cloned = (TransactionHistory) super.clone();
        cloned.recordOfAllTransactions = new StringBuilder(this.recordOfAllTransactions.toString());
        return cloned;
    }
}

