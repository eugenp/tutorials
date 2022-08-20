package com.baeldung.algorithms.luhn;

import org.junit.Assert;
import org.junit.Test;

public class LuhnCheckerUnitTest {

    @Test
    public void whenCardNumberDoesMeetLuhnCriteria_thenCheckLuhnReturnsTrue() {
        String cardNumber = "8649";
        boolean result = LuhnChecker.checkLuhn(cardNumber);
        Assert.assertTrue(result);
    }
    
    @Test
    public void whenCardNumberDoesNotMeetLuhnCriteria_thenCheckLuhnReturnsFalse() {
        String cardNumber = "8642";
        boolean result = LuhnChecker.checkLuhn(cardNumber);
        Assert.assertFalse(result);
    }
    
    @Test
    public void whenCardNumberHasNoSecondDigits_thenCheckLuhnCalculatesCorrectly() {
        String cardNumber = "0505050505050505";
        boolean result = LuhnChecker.checkLuhn(cardNumber);
        Assert.assertTrue(result);
    }

    @Test
    public void whenCardNumberHasSecondDigits_thenCheckLuhnCalculatesCorrectly() {
        String cardNumber = "75757575757575";
        boolean result = LuhnChecker.checkLuhn(cardNumber);
        Assert.assertTrue(result);
    }
    
    @Test
    public void whenDoubleAndSumDigitsIsCalled_thenOutputIsCorrect() {
        Assert.assertEquals(LuhnChecker.doubleAndSumDigits(0), 0);
        Assert.assertEquals(LuhnChecker.doubleAndSumDigits(1), 2);
        Assert.assertEquals(LuhnChecker.doubleAndSumDigits(2), 4);
        Assert.assertEquals(LuhnChecker.doubleAndSumDigits(3), 6);
        Assert.assertEquals(LuhnChecker.doubleAndSumDigits(4), 8);
        Assert.assertEquals(LuhnChecker.doubleAndSumDigits(5), 1);
        Assert.assertEquals(LuhnChecker.doubleAndSumDigits(6), 3);
        Assert.assertEquals(LuhnChecker.doubleAndSumDigits(7), 5);
        Assert.assertEquals(LuhnChecker.doubleAndSumDigits(8), 7);
        Assert.assertEquals(LuhnChecker.doubleAndSumDigits(9), 9);
    }
    
    @Test
    public void whenCardNumberNonNumeric_thenCheckLuhnReturnsFalse() {
        String cardNumber = "test";
        boolean result = LuhnChecker.checkLuhn(cardNumber);
        Assert.assertFalse(result);
    }
    
    @Test
    public void whenCardNumberIsNull_thenCheckLuhnReturnsFalse() {
        String cardNumber = null;
        boolean result = LuhnChecker.checkLuhn(cardNumber);
        Assert.assertFalse(result);
    }
}
