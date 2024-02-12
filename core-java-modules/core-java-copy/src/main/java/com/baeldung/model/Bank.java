package com.baeldung.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Bank implements Cloneable{
    private String name;
    private List<Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public Bank clone() {
        try {
            Bank clonedBank = (Bank) super.clone();
            // Perform deep copy of the accounts list
            clonedBank.accounts = new ArrayList<>(this.accounts.size());
            for (Account account : this.accounts) {
                clonedBank.addAccount(account.clone());
            }
            return clonedBank;
        } catch (CloneNotSupportedException e) {
            return new Bank(this.getName());
        }
    }
}
