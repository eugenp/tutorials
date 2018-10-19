package com.baeldung.autovalue;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoneyUnitTest {
    @Test
    public void givenTwoSameValueMoneyObjects_whenEqualityTestFails_thenCorrect() {
        MutableMoney m1 = new MutableMoney(10000, "USD");
        MutableMoney m2 = new MutableMoney(10000, "USD");
        assertFalse(m1.equals(m2));
    }

    @Test
    public void givenTwoSameValueMoneyValueObjects_whenEqualityTestPasses_thenCorrect() {
        ImmutableMoney m1 = new ImmutableMoney(10000, "USD");
        ImmutableMoney m2 = new ImmutableMoney(10000, "USD");
        assertTrue(m1.equals(m2));
    }

    @Test
    public void givenValueTypeWithAutoValue_whenFieldsCorrectlySet_thenCorrect() {
        AutoValueMoney m = AutoValueMoney.create("USD", 10000);
        assertEquals(m.getAmount(), 10000);
        assertEquals(m.getCurrency(), "USD");
    }

    @Test
    public void given2EqualValueTypesWithAutoValue_whenEqual_thenCorrect() {
        AutoValueMoney m1 = AutoValueMoney.create("USD", 5000);
        AutoValueMoney m2 = AutoValueMoney.create("USD", 5000);
        assertTrue(m1.equals(m2));
    }

    @Test
    public void given2DifferentValueTypesWithAutoValue_whenNotEqual_thenCorrect() {
        AutoValueMoney m1 = AutoValueMoney.create("GBP", 5000);
        AutoValueMoney m2 = AutoValueMoney.create("USD", 5000);
        assertFalse(m1.equals(m2));
    }

    @Test
    public void given2EqualValueTypesWithBuilder_whenEqual_thenCorrect() {
        AutoValueMoneyWithBuilder m1 = AutoValueMoneyWithBuilder.builder().setAmount(5000).setCurrency("USD").build();
        AutoValueMoneyWithBuilder m2 = AutoValueMoneyWithBuilder.builder().setAmount(5000).setCurrency("USD").build();
        assertTrue(m1.equals(m2));
    }

    @Test
    public void given2DifferentValueTypesBuilder_whenNotEqual_thenCorrect() {
        AutoValueMoneyWithBuilder m1 = AutoValueMoneyWithBuilder.builder().setAmount(5000).setCurrency("USD").build();
        AutoValueMoneyWithBuilder m2 = AutoValueMoneyWithBuilder.builder().setAmount(5000).setCurrency("GBP").build();
        assertFalse(m1.equals(m2));
    }

    @Test
    public void givenValueTypeWithBuilder_whenFieldsCorrectlySet_thenCorrect() {
        AutoValueMoneyWithBuilder m = AutoValueMoneyWithBuilder.builder().setAmount(5000).setCurrency("USD").build();
        assertEquals(m.getAmount(), 5000);
        assertEquals(m.getCurrency(), "USD");
    }
}
