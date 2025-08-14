package com.baeldung.vmlens;

public class VolatileFieldBankAccount {

    private volatile int amount;

    public int getAmount() {
        return amount;
    }

    public void update(int delta) {
        amount += delta;
    }
}
