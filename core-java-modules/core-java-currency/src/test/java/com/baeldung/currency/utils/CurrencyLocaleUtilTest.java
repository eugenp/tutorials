package com.baeldung.currency.utils;

import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyLocaleUtilTest {
    private final CurrencyLocaleUtil currencyLocale = new CurrencyLocaleUtil();

    @Test
    void givenLocale_whenGetSymbolForLocale_thenReturnsLocalizedSymbol() {
        assertEquals("$", currencyLocale.getSymbolForLocale(Locale.US));
        assertEquals("€", currencyLocale.getSymbolForLocale(Locale.FRANCE));
    }
}