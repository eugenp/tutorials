package com.baeldung.java_shallow_deep_copy.data;

public class BankAccount implements Cloneable {

    protected String name;
    protected String surname;
    protected Balance balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public BankAccount(String name, String surname, Balance balance) {
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }
}
