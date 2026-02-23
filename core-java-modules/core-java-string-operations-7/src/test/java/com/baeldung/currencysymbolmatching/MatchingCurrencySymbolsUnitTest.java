package com.baeldung.currencysymbolmatching;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class MatchingCurrencySymbolsUnitTest {

    private static final String DOLLAR_PATTERN = "^\\$\\d+(\\.\\d{1,2})?$";
    private static final String DOLLAR_WITH_COMMAS_PATTERN = "^\\$\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?$";

    boolean matchesDollarAmount(String input) {
        return Pattern.matches(DOLLAR_PATTERN, input);
    }

    boolean matchesDollarAmountWithCommas(String input) {
        return Pattern.matches(DOLLAR_WITH_COMMAS_PATTERN, input);
    }

    @Test
    void whenValidDollarAmount_thenMatches() {
        assertTrue(matchesDollarAmount("$100"));
        assertTrue(matchesDollarAmount("$100.00"));
        assertTrue(matchesDollarAmount("$10.5"));
        assertTrue(matchesDollarAmount("$0"));
        assertTrue(matchesDollarAmount("$0.99"));
    }

    @Test
    void whenInvalidDollarAmount_thenDoesNotMatch() {
        assertFalse(matchesDollarAmount("$$$34.00"));
        assertFalse(matchesDollarAmount("$10."));
        assertFalse(matchesDollarAmount("$10.123"));
        assertFalse(matchesDollarAmount("100.00"));
        assertFalse(matchesDollarAmount("$1,000.00"));
    }

    @Test
    void whenDollarAmountWithCommas_thenMatches() {
        assertTrue(matchesDollarAmountWithCommas("$1,000.00"));
        assertTrue(matchesDollarAmountWithCommas("$1,234,567.89"));
        assertTrue(matchesDollarAmountWithCommas("$100"));
        assertTrue(matchesDollarAmountWithCommas("$10.5"));
    }

    Number parseCurrency(String input) {
        try {
            return NumberFormat.getCurrencyInstance(Locale.US).parse(input);
        } catch (ParseException e) {
            return null;
        }
    }

    @Test
    void whenValidCurrencyFormat_thenParses() {
        assertNotNull(parseCurrency("$789.11"));
        assertNotNull(parseCurrency("$1,234.56"));
        assertNotNull(parseCurrency("$0.99"));
    }

    @Test
    void whenPartiallyValidInput_thenStillParses() {
        assertNotNull(parseCurrency("$12asdf"));
        assertNotNull(parseCurrency("$100abc"));
    }
}
