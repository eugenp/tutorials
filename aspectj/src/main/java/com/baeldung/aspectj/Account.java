package com.baeldung.aspectj;

public class Account {
    int balance = 20;

    public boolean withdraw(int amount) {
        if (balance - amount > 0) {
            balance = balance - amount;
            return true;
        } else
            return false;
    }
}
