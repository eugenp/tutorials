package com.baeldung.abaproblem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountUnitTest {

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
    }

    @Test
    public void zeroBalanceInitializationTest() {
        assertEquals(0, account.getBalance());
        assertEquals(0, account.getTransactionCount());
        assertEquals(0, account.getCurrentThreadCASFailureCount());
    }

    @Test
    public void depositTest() {
        final int moneyToDeposit = 50;

        assertTrue(account.deposit(moneyToDeposit));

        assertEquals(moneyToDeposit, account.getBalance());
    }

    @Test
    public void withdrawTest() throws InterruptedException {
        final int defaultBalance = 50;
        final int moneyToWithdraw = 20;

        account.deposit(defaultBalance);

        assertTrue(account.withdraw(moneyToWithdraw));

        assertEquals(defaultBalance - moneyToWithdraw, account.getBalance());
    }

    @Test
    public void abaProblemTest() throws InterruptedException {
        final int defaultBalance = 50;

        final int amountToWithdrawByThread1 = 20;
        final int amountToWithdrawByThread2 = 10;
        final int amountToDepositByThread2 = 10;

        assertEquals(0, account.getTransactionCount());
        assertEquals(0, account.getCurrentThreadCASFailureCount());
        account.deposit(defaultBalance);
        assertEquals(1, account.getTransactionCount());

        Thread thread1 = new Thread(() -> {

            // this will take longer due to the name of the thread
            assertTrue(account.withdraw(amountToWithdrawByThread1));

            // thread 1 fails to capture ABA problem
            assertNotEquals(1, account.getCurrentThreadCASFailureCount());

        }, "thread1");

        Thread thread2 = new Thread(() -> {

            assertTrue(account.deposit(amountToDepositByThread2));
            assertEquals(defaultBalance + amountToDepositByThread2, account.getBalance());

            // this will be fast due to the name of the thread
            assertTrue(account.withdraw(amountToWithdrawByThread2));

            // thread 1 didn't finish yet, so the original value will be in place for it
            assertEquals(defaultBalance, account.getBalance());

            assertEquals(0, account.getCurrentThreadCASFailureCount());
        }, "thread2");

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        // compareAndSet operation succeeds for thread 1
        assertEquals(defaultBalance - amountToWithdrawByThread1, account.getBalance());

        //but there are other transactions
        assertNotEquals(2, account.getTransactionCount());

        // thread 2 did two modifications as well
        assertEquals(4, account.getTransactionCount());
    }
}
