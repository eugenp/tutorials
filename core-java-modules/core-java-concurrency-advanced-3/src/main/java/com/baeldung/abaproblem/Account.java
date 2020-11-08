package com.baeldung.abaproblem;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Account {

    private AtomicInteger balance;
    private AtomicInteger transactionCount;
    private ThreadLocal<Integer> currentThreadCASFailureCount;

    public Account() {
        this.balance = new AtomicInteger(0);
        this.transactionCount = new AtomicInteger(0);
        this.currentThreadCASFailureCount = new ThreadLocal<>();
        this.currentThreadCASFailureCount.set(0);
    }

    public int getBalance() {
        return balance.get();
    }

    public int getTransactionCount() {
        return transactionCount.get();
    }

    public int getCurrentThreadCASFailureCount() {
        return currentThreadCASFailureCount.get();
    }

    public boolean withdraw(int amount) {
        int current = getBalance();
        maybeWait();
        boolean result = balance.compareAndSet(current, current - amount);
        if (result) {
            transactionCount.incrementAndGet();
        } else {
            int currentCASFailureCount = currentThreadCASFailureCount.get();
            currentThreadCASFailureCount.set(currentCASFailureCount + 1);
        }
        return result;
    }

    private void maybeWait() {
        if ("thread1".equals(Thread.currentThread().getName())) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean deposit(int amount) {
        int current = balance.get();
        boolean result = balance.compareAndSet(current, current + amount);
        if (result) {
            transactionCount.incrementAndGet();
        } else {
            int currentCASFailureCount = currentThreadCASFailureCount.get();
            currentThreadCASFailureCount.set(currentCASFailureCount + 1);
        }
        return result;
    }

}
