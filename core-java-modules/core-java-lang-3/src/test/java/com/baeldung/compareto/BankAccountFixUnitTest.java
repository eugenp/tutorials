package com.baeldung.compareto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BankAccountFixUnitTest {

    @Test
    public void givenComparisonBasedImpl_whenUsingSmallIntegers_thenComparisonWorks() {
        BankAccountFix accountOne = new BankAccountFix(5000);
        BankAccountFix accountTwo = new BankAccountFix(1000);
        int comparison = accountOne.compareTo(accountTwo);
        assertThat(comparison).isPositive();
    }

    @Test
    public void givenComparisonBasedImpl_whenUsingLargeIntegers_thenComparisonWorks() {
        BankAccountFix accountOne = new BankAccountFix(1900000000);
        BankAccountFix accountTwo = new BankAccountFix(-2000000000);
        int comparison = accountOne.compareTo(accountTwo);
        assertThat(comparison).isPositive();
    }

}
