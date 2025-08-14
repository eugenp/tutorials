package com.baeldung.vmlens;

public class AtomicBankAccount {

    private final Object LOCK = new Object();
    private volatile int amount;

    public int getAmount() {
        return amount;
    }

    public void update(int delta) {
        synchronized (LOCK) {
            amount += delta;
        }
    }
}
