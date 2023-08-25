package com.baeldung.prototype;

import java.util.List;

public class Account {

    private List<AccountType> accountTypes;

    public List<AccountType> getAccountTypes() {
        return accountTypes;
    }

    public void setAccountTypes(List<AccountType> accountTypes) {
        this.accountTypes = accountTypes;
    }

    public Account(List<AccountType> accountTypes) {
        this.accountTypes = accountTypes;
    }

    public enum AccountType {
        SAVINGS, LOAN, CURRENT
    }
}
