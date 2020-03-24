package com.baeldung.stm;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class AccountUnitTest {

    @Test
    public void givenAccount_whenDecrement_thenShouldReturnProperValue() {
        // given
        Account a = new Account(10);

        // when
        a.adjustBy(-5);

        // then
        assertThat(a.getBalance()).isEqualTo(5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenAccount_whenDecrementTooMuch_thenShouldThrow() {
        // given
        Account a = new Account(10);

        // when
        a.adjustBy(-11);
    }

    @Test
    public void givenTwoThreads_whenBothApplyOperation_thenShouldThrow() throws InterruptedException {
        // given
        ExecutorService ex = Executors.newFixedThreadPool(2);
        Account a = new Account(10);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicBoolean exceptionThrown = new AtomicBoolean(false);

        // when
        ex.submit(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                a.adjustBy(-6);
            } catch (IllegalArgumentException e) {
                exceptionThrown.set(true);
            }
        });
        ex.submit(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                a.adjustBy(-5);
            } catch (IllegalArgumentException e) {
                exceptionThrown.set(true);
            }
        });

        countDownLatch.countDown();
        ex.awaitTermination(1, TimeUnit.SECONDS);
        ex.shutdown();

        // then
        assertTrue(exceptionThrown.get());
    }

    @Test
    public void givenTwoAccounts_whenFailedWhileTransferring_thenShouldRollbackTransaction() {
        // given
        final Account a = new Account(10);
        final Account b = new Account(10);

        // when
        a.transferTo(b, 5);

        // then
        assertThat(a.getBalance()).isEqualTo(5);
        assertThat(b.getBalance()).isEqualTo(15);

        // and
        try {
            a.transferTo(b, 20);
        } catch (final IllegalArgumentException e) {
            System.out.println("failed to transfer money");
        }

        // then
        assertThat(a.getBalance()).isEqualTo(5);
        assertThat(b.getBalance()).isEqualTo(15);
    }

    @Test
    public void givenTwoThreads_whenBothTryToTransfer_thenShouldNotDeadlock() throws InterruptedException {
        // given
        ExecutorService ex = Executors.newFixedThreadPool(2);
        final Account a = new Account(10);
        final Account b = new Account(10);
        CountDownLatch countDownLatch = new CountDownLatch(1);

        // when
        ex.submit(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a.transferTo(b, 10);
        });
        ex.submit(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            b.transferTo(a, 1);

        });

        countDownLatch.countDown();
        ex.awaitTermination(1, TimeUnit.SECONDS);
        ex.shutdown();

        // then
        assertThat(a.getBalance()).isEqualTo(1);
        assertThat(b.getBalance()).isEqualTo(19);
    }

}