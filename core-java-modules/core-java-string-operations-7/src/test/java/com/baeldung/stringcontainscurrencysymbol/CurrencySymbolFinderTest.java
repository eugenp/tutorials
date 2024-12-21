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
    public void givenCurrencyCode_whenCheckingString_validationPass() {
        String input = "750CHF";
        String currencyCode = Currency.getInstance(new Locale("", "CH"))
            .getCurrencyCode();

        String value = currencyInputMatcher(input, currencyCode);

        assertEquals(input, value);
    }

@Test
public void givenPoundsWithMatchingCountryCode_whenCheckingString_validationPass() {
    String input = "£599,000";

    float value = validateCurrencyAmount(Locale.UK, input).floatValue();

    assertEquals(599000.0, value, 0);
}

    @Test
    public void givenRupeeWithMatchingCountryCode_whenCheckingString_validationPass() {
        String input = "₹1,00,00,000";

        float value = validateCurrencyAmount(new Locale("hi", "IN"), input).floatValue();

        assertEquals(10000000, value, 0);
    }
}
