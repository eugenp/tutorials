package com.baeldung.vmlens;

public class RegularFieldBankAccount {

    private int amount;

    public int getAmount() {
        return amount;
    }

    public void update(int delta) {
        amount += delta;
    }
}
