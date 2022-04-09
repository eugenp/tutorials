package com.baeldung.atomicstampedreference;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class StampedAccount {

    private final AtomicInteger stamp = new AtomicInteger(0);
    private final AtomicStampedReference<Integer> account = new AtomicStampedReference<>(0, 0);

    public boolean deposit(int funds) {
        int[] stamps = new int[1];
        int current = this.account.get(stamps);
        int newStamp = this.stamp.incrementAndGet();

        // Thread is paused here to allow other threads to update the stamp and amount (for testing only)
        sleep();

        return this.account.compareAndSet(current, current + funds, stamps[0], newStamp);
    }

    public boolean withdrawal(int funds) {
        int[] stamps = new int[1];
        int current = this.account.get(stamps);
        int newStamp = this.stamp.incrementAndGet();
        return this.account.compareAndSet(current, current - funds, stamps[0], newStamp);
    }

    public int getBalance() {
        return account.getReference();
    }

    public int getStamp() {
        return account.getStamp();
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ignored) {
        }
    }
}
