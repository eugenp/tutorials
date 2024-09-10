package com.baeldung.stringcontainscurrencysymbol;

import org.junit.Test;

import java.util.Locale;

import static com.baeldung.stringcontainscurrencysymbol.CurrencySymbolFinder.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CurrencySymbolFinderTest {

    private static final String SINGLE_SIGN_REGEX = "\\p{Sc}";
    private static final String MULTIPLE_SIGNS_REGEX = "^\\p{Sc}{3}[1-9]";

    @Test
    public void givenCurrencySymbol_whenCheckingString_returnSymbol() {
        String usdValue = "$4,098.09";
        String symbol = singleSymbolMatcher(SINGLE_SIGN_REGEX, usdValue);
        assertEquals("$", symbol);
    }

    @Test
    public void givenCurrencyName_whenCheckingString_returnSymbol() {
        String chfValue = "750CHF";
        String currencyName = currencyNameMatcher(new Locale("", "CH"), chfValue);
        assertEquals("CHF", currencyName);
    }

    @Test
    public void givenMultipleCurrencySymbols_whenCheckingString_returnTrue() {
        String poundValue = "£££4,098.09";
        boolean symbol = multipleSymbolMatcher(MULTIPLE_SIGNS_REGEX, poundValue);
        assertTrue(symbol);
    }

}
