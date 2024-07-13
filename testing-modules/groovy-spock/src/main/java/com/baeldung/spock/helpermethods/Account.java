package com.baeldung.spock.helpermethods;

import java.math.BigDecimal;

public class Account {

    private Address address;

    private String accountName;

    private BigDecimal currentBalance;

    private long overdraftLimit;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public long getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(long overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
}
