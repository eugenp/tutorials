package com.baeldung.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class SafeAccount {

    AtomicInteger stamp = new AtomicInteger(0);
    AtomicStampedReference<Integer> balance = new AtomicStampedReference<>(0, 0);

    public int getBalance() {
        return this.balance.get(new int[1]);
    }

    public int getStamp() {
        int[] stamps = new int[1];
        this.balance.get(stamps);
        return stamps[0];
    }

    public boolean deposit(int funds) {
        int[] stamps = new int[1];
        int current = this.balance.get(stamps);
        int newStamp = this.stamp.incrementAndGet();
        return this.balance.compareAndSet(current, current + funds, stamps[0], newStamp);
    }

    public boolean withdrawal(int funds) {
        int[] stamps = new int[1];
        int current = this.balance.get(stamps);
        int newStamp = this.stamp.incrementAndGet();
        return this.balance.compareAndSet(current, current - funds, stamps[0], newStamp);
    }
}
