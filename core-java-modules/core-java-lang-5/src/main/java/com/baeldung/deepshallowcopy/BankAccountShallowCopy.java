package com.baeldung.deepshallowcopy;

import java.util.List;

class BankAccountShallowCopy implements Cloneable {
    private String accountNumber;
    private List<String> transactions;

    public BankAccountShallowCopy(String accountNumber, List<String> transactions) {
        this.accountNumber = accountNumber;
        this.transactions = transactions;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // Shallow copy
    }
}

