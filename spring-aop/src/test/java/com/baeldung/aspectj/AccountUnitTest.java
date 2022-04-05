package com.baeldung.aspectj;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountUnitTest {
    private Account account;

    @Before
    public void before() {
        account = new Account();
    }

    @Test
    public void givenBalance20AndMinBalance10_whenWithdraw5_thenSuccess() {
        assertTrue(account.withdraw(5));
    }

    @Test
    public void givenBalance20AndMinBalance10_whenWithdraw100_thenFail() {
        assertFalse(account.withdraw(100));
    }
}
