package com.baeldung.java_shallow_deep_copy.data;

public class BankAccountDeep extends BankAccount {

    public BankAccountDeep(String name, String surname, Balance balance) {
        super(name, surname, balance);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new BankAccountDeep(name, surname, (Balance) balance.clone());
    }

    public BankAccountDeep(BankAccountDeep bankAccountDeep) {
        this(bankAccountDeep.name, bankAccountDeep.surname, new Balance(bankAccountDeep.balance));
    }
}
