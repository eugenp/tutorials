package com.baeldung.atomicstampedreference;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class StampedAccount {

    private AtomicInteger stamp = new AtomicInteger(0);
    private AtomicStampedReference<Integer> account = new AtomicStampedReference<>(0, 0);

    public int getBalance() {
        return this.account.get(new int[1]);
    }

    public int getStamp() {
        int[] stamps = new int[1];
        this.account.get(stamps);
        return stamps[0];
    }

    public boolean deposit(int funds) {
        int[] stamps = new int[1];
        int current = this.account.get(stamps);
        int newStamp = this.stamp.incrementAndGet();
        return this.account.compareAndSet(current, current + funds, stamps[0], newStamp);
    }

    public boolean withdrawal(int funds) {
        int[] stamps = new int[1];
        int current = this.account.get(stamps);
        int newStamp = this.stamp.incrementAndGet();
        return this.account.compareAndSet(current, current - funds, stamps[0], newStamp);
    }
}
