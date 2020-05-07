package com.baeldung.abaproblem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertTrue(account.getTransactionDates().isEmpty());
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
    public void withdrawWithoutSufficientBalanceTest() {
        assertThrows(RuntimeException.class, () -> account.withdraw(10));
    }

    @Test
    public void abaProblemTest() throws InterruptedException {
        final int defaultBalance = 50;

        final int amountToWithdrawByThreadA = 20;
        final int amountToWithdrawByThreadB = 10;
        final int amountToDepositByThreadB = 10;

        assertTrue(account.getTransactionDates().isEmpty());
        account.deposit(defaultBalance);
        assertEquals(1, account.getTransactionDates().size());

        Thread threadA = new Thread(() -> {
            try {
                // this will take longer due to the name of the thread
                assertTrue(account.withdraw(amountToWithdrawByThreadA));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "thread1");

        Thread threadB = new Thread(() -> {

            assertTrue(account.deposit(amountToDepositByThreadB));
            assertEquals(defaultBalance + amountToDepositByThreadB, account.getBalance());
            try {
                // this will be fast due to the name of the thread
                assertTrue(account.withdraw(amountToWithdrawByThreadB));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // thread 1 didn't finish yet, so the original value will be in place for it
            assertEquals(defaultBalance, account.getBalance());

        }, "thread2");

        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();

        // compareAndSet operation succeeds for thread 1
        assertEquals(defaultBalance - amountToWithdrawByThreadA, account.getBalance());

        //but there are other transactions
        assertNotEquals(2, account.getTransactionDates().size());

        // thread 2 did two modifications as well
        assertEquals(4, account.getTransactionDates().size());
    }
}
