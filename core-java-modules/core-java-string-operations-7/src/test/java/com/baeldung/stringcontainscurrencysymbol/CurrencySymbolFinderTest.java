package com.baeldung.stringcontainscurrencysymbol;

import static com.baeldung.stringcontainscurrencysymbol.CurrencySymbolFinder.currencyInputMatcher;
import static com.baeldung.stringcontainscurrencysymbol.CurrencySymbolFinder.validateCurrencyAmount;
import static org.junit.Assert.assertEquals;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CurrencySymbolFinderTest {

    @ParameterizedTest
    @ValueSource(strings = { "$4,100.00", "₹5 000.25", "€85.50", "£0.75" })
    public void givenCurrencySymbol_whenCheckingString_validationPass(String input) {
        String currencySymbol = input.substring(0, 1);
        String value = currencyInputMatcher(input, currencySymbol);

        assertEquals(input, value);
    }

    @Test
    public void givenLocaleInstance_whenCheckingString_validationPass() {
        String input = "750CHF";
        String currencySymbol = Currency.getInstance(new Locale("", "CH"))
            .getSymbol();
        String value = currencyInputMatcher(input, currencySymbol);

        assertEquals(input, value);
    }

    @Test
    public void givenPoundsWithMatchingCountryCode_whenCheckingString_validationPass() {
        String poundValue = "£599,000";
        float value = validateCurrencyAmount(Locale.UK, poundValue).floatValue();

        assertEquals(599000.0, value, 0);
    }

    @Test
    public void givenRupeeWithMatchingCountryCode_whenCheckingString_validationPass() {
        String rupeeValue = "₹1,00,00,000";
        float value = validateCurrencyAmount(new Locale("hi", "IN"), rupeeValue).floatValue();

        assertEquals(10000000, value, 0);
    }
}
