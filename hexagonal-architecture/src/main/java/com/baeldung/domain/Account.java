package com.baeldung.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Account implements Serializable {

    private static final long serialVersionUID = 2892929922L;
    private double balance;
    private LocalDate dateOpened;
    private boolean isActive;
    private String accountNumber;
    private String customerId;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setDateOpened(LocalDate dateOpened) {
        this.dateOpened = dateOpened;
    }

    public LocalDate getDateOpened() {
        return dateOpened;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String toString() {
        return "[balance " + balance + " accountnumber " + accountNumber + " Active " + isActive + " Date Opened "
                + dateOpened + "]";

    }

}
