package com.baeldung.atomicstampedreference;

import org.junit.Assert;
import org.junit.Test;

public class ThreadStampedAccountUnitTest {

    @Test
    public void givenMultiThread_whenStampedAccount_thenSetBalance() throws InterruptedException {
        StampedAccount account = new StampedAccount();
        Thread t = new Thread(() -> {
            while (!account.withdrawal(100))
                Thread.yield();
        });
        t.start();
        Assert.assertTrue(account.deposit(100));
        t.join(1_000);
        Assert.assertFalse(t.isAlive());
        Assert.assertSame(0, account.getBalance());
    }
}
