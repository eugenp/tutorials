package com.baeldung.java_shallow_deep_copy.data;

public class BankAccountShallow extends BankAccount {

    public BankAccountShallow(String name, String surname, Balance balance) {
        super(name, surname, balance);
    }

    public BankAccountShallow(BankAccountShallow shallow) {
        this(shallow.name, shallow.surname, shallow.balance);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
