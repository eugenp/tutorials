package com.baeldung.currency.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyMapUtilTest {
    @Test
    void givenValidCurrencyCode_whenGetSymbol_thenReturnsCorrectSymbol() {
        assertEquals("$", CurrencyMapUtil.getSymbol("USD"));
        assertEquals("€", CurrencyMapUtil.getSymbol("EUR"));
        assertEquals("₹", CurrencyMapUtil.getSymbol("INR"));
    }

    @Test
    void givenInvalidCurrencyCode_whenGetSymbol_thenReturnsUnknown() {
        assertEquals("Unknown", CurrencyMapUtil.getSymbol("XYZ"));
    }
}