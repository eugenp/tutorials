package com.baeldung.abaproblem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Account {

    private AtomicInteger balance = new AtomicInteger(0);
    private List<Long> transactionDates = new ArrayList<>();

    public int getBalance() {
        return balance.get();
    }

    public List<Long> getTransactionDates() {
        return transactionDates;
    }

    public boolean withdraw(int amount) throws InterruptedException {
        int current = getBalance();
        if (current < amount) {
            throw new RuntimeException("Not sufficient balance");
        }
        precessBalance();
        boolean result = balance.compareAndSet(current, current - amount);
        if (result) {
            transactionDates.add(System.currentTimeMillis());
        }
        return result;
    }

    private void precessBalance() throws InterruptedException {
        if ("thread1".equals(Thread.currentThread().getName())) {
            TimeUnit.SECONDS.sleep(2);
        }
    }

    public boolean deposit(int amount) {
        int current = balance.get();
        boolean result = balance.compareAndSet(current, current + amount);
        if (result) {
            transactionDates.add(System.currentTimeMillis());
        }
        return result;
    }

}
