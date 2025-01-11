package com.baeldung.validatestringnumber;

import static com.baeldung.validatestringnumber.StringNumberValidator.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

public class StringNumberValidatorUnitTest {

    @Test
    public void whenUsingCharacterLooping_thenReturnExpectedResult() {
        assertTrue(isNumeric("123"));
        assertTrue(isNumeric("123.45"));
        assertFalse(isNumeric("123a"));
        assertFalse(isNumeric("123.45.67"));
        assertFalse(isNumeric(""));
        assertFalse(isNumeric("."));
        assertFalse(isNumeric(null));
    }

    @Test
    public void whenUsingBuiltInParsing_thenReturnExpectedResult() {
        assertTrue(isInteger("123"));
        assertFalse(isInteger("123.45"));
        assertFalse(isInteger("abc"));

        assertTrue(isDouble("123.45"));
        assertTrue(isDouble("123"));
        assertFalse(isDouble("12a"));

        assertTrue(isBigDecimal("123.45"));
        assertTrue(isBigDecimal("123"));
        assertTrue(isBigDecimal("1.23E3"));
        assertFalse(isBigDecimal("123abc"));
        assertFalse(isBigDecimal(null));
    }

    @Test
    public void whenUsingRegex_thenReturnExpectedResult() {
        assertTrue(isValidNumberRegex("123"));
        assertTrue(isValidNumberRegex("-123.45"));
        assertTrue(isValidNumberRegex("+123.45"));
        assertTrue(isValidNumberRegex("1.23E3"));
        assertFalse(isValidNumberRegex("123a"));
        assertFalse(isValidNumberRegex("123..45"));
    }

    @Test
    public void whenUsingNumberUtils_thenReturnExpectedResult() {
        assertTrue(NumberUtils.isCreatable("123"));
        assertTrue(NumberUtils.isCreatable("123.45"));
        assertTrue(NumberUtils.isCreatable("-123.45"));
        assertTrue(NumberUtils.isCreatable("1.23E3"));
        assertFalse(NumberUtils.isCreatable("123a"));
        assertFalse(NumberUtils.isCreatable("12.3.45"));
        assertFalse(NumberUtils.isCreatable(""));
    }
}
