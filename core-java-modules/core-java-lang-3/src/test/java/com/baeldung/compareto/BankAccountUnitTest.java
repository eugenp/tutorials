package com.baeldung.compareto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class BankAccountUnitTest {

    @Test
    public void givenSubtractionBasedImpl_whenUsingSmallIntegers_thenComparisonWorks() {
        BankAccount accountOne = new BankAccount(5000);
        BankAccount accountTwo = new BankAccount(1000);
        int comparison = accountOne.compareTo(accountTwo);
        assertThat(comparison).isPositive();
    }

    @Test
    public void givenSubtractionBasedImpl_whenUsingLargeIntegers_thenComparisonBreaks() {
        BankAccount accountOne = new BankAccount(1900000000);
        BankAccount accountTwo = new BankAccount(-2000000000);
        int comparison = accountOne.compareTo(accountTwo);
        assertThat(comparison).isNegative();
    }

}
