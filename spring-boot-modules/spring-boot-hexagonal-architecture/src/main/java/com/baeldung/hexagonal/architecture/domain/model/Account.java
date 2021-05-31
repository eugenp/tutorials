package com.baeldung.hexagonal.architecture.domain.model;

import java.util.Objects;

/**
 * The class defines the domain object model
 */
public class Account {

    private Integer accountId;
    private String customerName;
    private String accountType;
    private Double balance;
    private String address;

    public Account() {
        super();
    }

    public Account(Integer accountId, String customerName, String accountType, Double balance, String address) {
        super();
        this.accountId = accountId;
        this.customerName = customerName;
        this.accountType = accountType;
        this.balance = balance;
        this.address = address;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer AccountId) {
        this.accountId = AccountId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Account Account = (Account) o;
        return Objects.equals(accountId, Account.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}
