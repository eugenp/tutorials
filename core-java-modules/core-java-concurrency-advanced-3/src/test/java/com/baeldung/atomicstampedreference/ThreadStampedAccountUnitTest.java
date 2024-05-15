package com.baeldung.atomicstampedreference;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ThreadStampedAccountUnitTest {

    @Test
    public void givenMultiThread_whenStampedAccount_thenSetBalance() throws InterruptedException {
        StampedAccount account = new StampedAccount();

        Thread t = new Thread(() -> {
            while (!account.deposit(100)) {
                Thread.yield();
            }
        });
        t.start();

        Thread t2 = new Thread(() -> {
            while (!account.withdrawal(100)) {
                Thread.yield();
            }
        });
        t2.start();

        t.join(10_000);
        t2.join(10_000);

        assertFalse(t.isAlive());
        assertFalse(t2.isAlive());

        assertEquals(0, account.getBalance());
        assertTrue(account.getStamp() > 0);
    }
}
