package com.baeldung.lombok.configexamples;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountUnitTest {

    @Test
    void should_initialize_account() {
        Account myAccount = new Account()
            .setBalance(2000.00)
            .setAccountHolder("John Snow");

        assertEquals(2000.00, myAccount.getBalance());
        assertEquals("John Snow", myAccount.getAccountHolder());
    }

    @Test
    void should_throw_error_when_balance_becomes_negative() {
        Account myAccount = new Account()
            .setBalance(20.00)
            .setAccountHolder("John Snow");

        assertThrows(IllegalArgumentException.class, () -> myAccount.withdraw(100.00));
    }

    @Test
    void should_throw_no_error_when_balance_becomes_zero() {
        Account myAccount = new Account()
            .setBalance(20.00)
            .setAccountHolder("John Snow");

        myAccount.withdraw(20.00);

        assertEquals(0.00, myAccount.getBalance());
    }

    @Test
    void should_update_balance_properly() {
        Account myAccount = new Account()
            .setBalance(20.00)
            .setAccountHolder("John Snow");

        myAccount.withdraw(5.00).withdraw(10.00);

        assertEquals(5.00, myAccount.getBalance());
    }
}