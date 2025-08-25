package com.baeldung.vmlens;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;

import org.junit.jupiter.api.Test;

import com.vmlens.api.AllInterleavings;

/**
 * This test tests the different implementations of a concurrent bank account class.+
 * To see what is wrong with RegularFieldBankAccount and VolatileFieldBankAccount replace
 * AtomicBankAccount with one of those classes.
 *
 */

class BankAccountTest {

    @Test
    public void whenParallelUpdate_thenAmountSumOfBothUpdates() throws InterruptedException {
        try (AllInterleavings allInterleavings = new AllInterleavings("bankAccount.updateUpdate")) {
            while (allInterleavings.hasNext()) {
                AtomicBankAccount bankAccount = new AtomicBankAccount();

                Thread first = new Thread() {
                    @Override
                    public void run() {
                        bankAccount.update(5);
                    }
                };
                first.start();
                bankAccount.update(10);
                first.join();

                int amount = bankAccount.getAmount();
                assertThat(amount, is(15));
            }
        }
    }

    @Test
    public void whenParallelUpdateAndGet_thenResultEitherAmountBeforeOrAfterUpdate() throws InterruptedException {
        try (AllInterleavings allInterleavings = new AllInterleavings("bankAccount.updateGetAmount")) {
            while (allInterleavings.hasNext()) {
                AtomicBankAccount bankAccount = new AtomicBankAccount();

                Thread first = new Thread() {
                    @Override
                    public void run() {
                        bankAccount.update(5);
                    }
                };
                first.start();

                int amount = bankAccount.getAmount();

                assertThat(amount, anyOf(is(0), is(5)));
                first.join();
            }
        }
    }
}
