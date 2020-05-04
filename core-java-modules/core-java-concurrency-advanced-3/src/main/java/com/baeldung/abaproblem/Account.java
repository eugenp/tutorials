package com.baeldung.abaproblem;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Account {

    private AtomicInteger balance = new AtomicInteger(0);

    public int getBalance() {
        return balance.get();
    }

    public boolean withdraw(int amount) throws InterruptedException {
        int current = getBalance();
        if (current < amount) {
            throw new RuntimeException("Not sufficient balance");
        }
        precessBalance();
        return balance.compareAndSet(current, current - amount);
    }

    private void precessBalance() throws InterruptedException {
        if ("thread 1".equals(Thread.currentThread().getName())) {
            TimeUnit.SECONDS.sleep(2);
        }
    }

    public boolean deposit(int amount) {
        int current = balance.get();
        return balance.compareAndSet(current, current + amount);
    }

}
