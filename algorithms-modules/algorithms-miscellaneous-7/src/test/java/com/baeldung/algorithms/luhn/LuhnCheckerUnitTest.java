package com.baeldung.algorithms.luhn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LuhnCheckerUnitTest {

    @Test
    void whenCardNumberDoesMeetLuhnCriteria_thenCheckLuhnReturnsTrue() {
        String cardNumber = "8649";
        boolean result = LuhnChecker.checkLuhn(cardNumber);
        assertTrue(result);
    }
    
    @Test
    void whenCardNumberDoesNotMeetLuhnCriteria_thenCheckLuhnReturnsFalse() {
        String cardNumber = "8642";
        boolean result = LuhnChecker.checkLuhn(cardNumber);
        assertFalse(result);
    }
    
    @Test
    void whenCardNumberHasNoSecondDigits_thenCheckLuhnCalculatesCorrectly() {
        String cardNumber = "0505050505050505";
        boolean result = LuhnChecker.checkLuhn(cardNumber);
        assertTrue(result);
    }

    @Test
    void whenCardNumberHasSecondDigits_thenCheckLuhnCalculatesCorrectly() {
        String cardNumber = "75757575757575";
        boolean result = LuhnChecker.checkLuhn(cardNumber);
        assertTrue(result);
    }
    
    @Test
    void whenDoubleAndSumDigitsIsCalled_thenOutputIsCorrect() {
        assertEquals(0,LuhnChecker.doubleAndSumDigits(0));
        assertEquals(2,LuhnChecker.doubleAndSumDigits(1));
        assertEquals(4, LuhnChecker.doubleAndSumDigits(2));
        assertEquals(6, LuhnChecker.doubleAndSumDigits(3));
        assertEquals(8, LuhnChecker.doubleAndSumDigits(4));
        assertEquals(1, LuhnChecker.doubleAndSumDigits(5));
        assertEquals(3, LuhnChecker.doubleAndSumDigits(6));
        assertEquals(5, LuhnChecker.doubleAndSumDigits(7));
        assertEquals(7, LuhnChecker.doubleAndSumDigits(8));
        assertEquals(9, LuhnChecker.doubleAndSumDigits(9));
    }
    
    @Test
    void whenCardNumberNonNumeric_thenCheckLuhnReturnsFalse() {
        String cardNumber = "test";
        boolean result = LuhnChecker.checkLuhn(cardNumber);
        assertFalse(result);
    }
    
    @Test
    void whenCardNumberIsNull_thenCheckLuhnReturnsFalse() {
        String cardNumber = null;
        boolean result = LuhnChecker.checkLuhn(cardNumber);
        assertFalse(result);
    }
}
