package com.baeldung.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Customer implements Cloneable{
    private String name;
    private int age;
    private Account account;

    public Customer(String name, int age, Account account) {
        this.name = name;
        this.age = age;
        this.account = account;
    }

    // Copy constructor for deep copying
    public Customer(Customer customer) {
        this(customer.getName(), customer.getAge(), new Account(customer.getAccount()));
    }

    @Override
    public Customer clone() {
        Customer customer = null;
        try {
            customer = (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            customer = new Customer(this.getName(), this.getAge(), this.getAccount());
        }
        customer.account = (Account) this.account.clone();
        return customer;
    }
}
