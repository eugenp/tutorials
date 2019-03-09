package com.baeldung.constructors;

import java.time.LocalDateTime;

class BankAccount {
    String name;
    LocalDateTime opened;
    double balance;
    
    @Override
    public String toString() {
        return String.format("%s, %s, %f", this.name, this.opened.toString(), this.balance);
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getOpened() {
        return opened;
    }

    public double getBalance() {
        return this.balance;
    }
}

class BankAccountEmptyConstructor extends BankAccount {
    public BankAccountEmptyConstructor() {
        this.name = "";
        this.opened = LocalDateTime.now();
        this.balance = 0.0d;
    }
}

class BankAccountParameterizedConstructor extends BankAccount {
    public BankAccountParameterizedConstructor(String name, LocalDateTime opened, double balance) {
        this.name = name;
        this.opened = opened;
        this.balance = balance;
    }
}

class BankAccountCopyConstructor extends BankAccount {
    public BankAccountCopyConstructor(String name, LocalDateTime opened, double balance) {
        this.name = name;
        this.opened = opened;
        this.balance = balance;
    }
        
    public BankAccountCopyConstructor(BankAccount other) {
        this.name = other.name;
        this.opened = LocalDateTime.now();
        this.balance = 0.0f;
    }
}
