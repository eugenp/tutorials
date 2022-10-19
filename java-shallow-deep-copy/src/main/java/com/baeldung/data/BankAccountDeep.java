package com.baeldung.data;

public class BankAccountDeep extends BankAccount {

    public BankAccountDeep(String name, String surname, Balance balance) {
        super(name, surname, balance);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        BankAccountDeep bankAccountDeep = (BankAccountDeep) super.clone();
        bankAccountDeep.setBalance((Balance) balance.clone());
        return bankAccountDeep;
    }

    public BankAccountDeep(BankAccountDeep bankAccountDeep) {
        this(bankAccountDeep.name, bankAccountDeep.surname, new Balance(bankAccountDeep.balance));
    }
}
