package com.baeldung.gson.entities;

import com.google.gson.annotations.Expose;


import java.util.List;

public class Person {

    public Person(String firstName, String lastName, String emailAddress, String password, List<BankAccount> bankAccounts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.bankAccounts = bankAccounts;
    }

    @Expose(serialize = true)
    private String firstName;
    @Expose(serialize = true)
    private String lastName;
    @Expose()
    private String emailAddress;
    @Expose(serialize = false)
    private String password;

    @Expose(serialize = true)
    private List<BankAccount> bankAccounts;

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
