package com.baeldung.stringcontainscurrencysymbol;

import static com.baeldung.stringcontainscurrencysymbol.CurrencySymbolFinder.currencyNameMatcher;
import static com.baeldung.stringcontainscurrencysymbol.CurrencySymbolFinder.currencySymbolMatcher;
import static com.baeldung.stringcontainscurrencysymbol.CurrencySymbolFinder.parseCurrencyAmount;
import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CurrencySymbolFinderTest {

    private static final String REGEX = "^\\p{Sc}?\\d{1,3}(,\\d{3})*(\\.\\d{2})?$";

    @ParameterizedTest
    @ValueSource(strings = { "$4,100.00", "€85.50", "£75", "₹500.25" })
    public void givenCurrencySymbol_whenCheckingString_returnSymbol(String input) {
        String value = currencySymbolMatcher(REGEX, input);
        assertEquals(input, value);
    }

    @Test
    public void givenLocaleInstance_whenCheckingString_returnSymbol() {
        String chfValue = "750CHF";
        String currencyName = currencyNameMatcher(new Locale("", "CH"), chfValue);
        assertEquals("CHF", currencyName);
    }

    @Test
    public void givenPoundsWithMatchingCountryCode_whenCheckingString_validationPass() {
        String poundValue = "£599000";
        float value = parseCurrencyAmount(Locale.UK, poundValue).floatValue();
        assertEquals(599000.0, value, 0);
    }

    @Test
    public void givenRupeeWithMatchingCountryCode_whenCheckingString_validationPass() {
        String rupeeValue = "₹1,00,00,000";
        float value = parseCurrencyAmount(new Locale("hi", "IN"), rupeeValue).floatValue();
        assertEquals(10000000, value, 0);
    }
}
