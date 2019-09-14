package com.baeldung.equalshashcode;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoneyUnitTest {

    @Test
    public void givenMoneyInstancesWithSameAmountAndCurrency_whenEquals_thenReturnsTrue() {
        Money income = new Money(55, "USD");
        Money expenses = new Money(55, "USD");

        assertTrue(income.equals(expenses));
    }

    @Test
    public void givenMoneyAndVoucherInstances_whenEquals_thenReturnValuesArentSymmetric() {
        Money cash = new Money(42, "USD");
        WrongVoucher voucher = new WrongVoucher(42, "USD", "Amazon");

        assertFalse(voucher.equals(cash));
        assertTrue(cash.equals(voucher));
    }

}
