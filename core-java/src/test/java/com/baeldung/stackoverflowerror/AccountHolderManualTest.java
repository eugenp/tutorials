package com.baeldung.stackoverflowerror;

import org.junit.Test;

public class AccountHolderManualTest {
    @Test(expected = StackOverflowError.class)
    public void whenInstanciatingAccountHolder_thenThrowsException() {
        AccountHolder holder = new AccountHolder();
    }
}
