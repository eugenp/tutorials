package com.baeldung.prototype;

import com.baeldung.prototype.Account.AccountType;

public class BankAccount {

    private int accountId;

    private AccountType accountName;

    private Account accountType;

    public BankAccount(int accountId, AccountType accountName, Account accountType) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountType = accountType;
    }

    public int getAccountId() {
        return accountId;
    }

    public AccountType getAccountName() {
        return accountName;
    }

    public Account getAccountType() {
        return accountType;
    }

    public void setAccountType(Account accountType) {
        this.accountType = accountType;
    }

}
