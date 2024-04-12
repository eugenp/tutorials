package com.baeldung.gson.entities;

import com.google.gson.annotations.Expose;

public class BankAccount {
    @Expose(serialize = false, deserialize = false)
    private String accountNumber;
    @Expose(serialize = true, deserialize = true)
    private String bankName;

    public BankAccount(String accountNumber, String bankName) {
        this.accountNumber = accountNumber;
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
