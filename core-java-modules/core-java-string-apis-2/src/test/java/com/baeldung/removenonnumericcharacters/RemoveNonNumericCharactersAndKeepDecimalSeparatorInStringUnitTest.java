package com.baeldung.removenonnumericcharacters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DecimalFormat;
import java.util.Locale;

import org.apache.commons.lang3.RegExUtils;
import org.junit.jupiter.api.Test;

import com.google.common.base.CharMatcher;

public class RemoveNonNumericCharactersAndKeepDecimalSeparatorInStringUnitTest {

    @Test
    void givenAString_whenRemovingUsingRegexAndReplaceAllMethod_thenShouldGetExpectedResult() {
        String s = "Testing abc123.555abc";
        s = s.replaceAll("[^\\d.]", "");
        assertEquals("123.555", s);
    }

    @Test
    void givenAString_whenRemovingUsingJava8Stream_thenShouldGetExpectedResult() {
        String s = "Testing abc123.555abc";
        StringBuilder sb = new StringBuilder();
        s.chars()
          .mapToObj(c -> (char) c)
          .filter(c -> Character.isDigit(c) || c == '.')
          .forEach(sb::append);
        assertEquals("123.555", sb.toString());
    }

    @Test
    void givenAString_whenRemovingUsingGuavaLibrary_thenShouldGetExpectedResult() {
        String s = "Testing abc123.555abc";
        String result = CharMatcher.inRange('0', '9')
          .or(CharMatcher.is('.'))
          .retainFrom(s);
        assertEquals("123.555", result);
    }

    @Test
    void givenAString_whenRemovingUsingApacheCommonsLibrary_thenShouldGetExpectedResult() {
        String s = "Testing abc123.555abc";
        String result = RegExUtils.removeAll(s, "[^\\d.]");
        assertEquals("123.555", result);
    }

    @Test
    void givenAStringWithCommaAsDecimalSeparator_whenRemovingUsingRegexAndReplaceAllMethod_thenShouldGetExpectedResult() {
        double d = 10.01d;
        String s = String.format(Locale.GERMAN, "%.2f", d);
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance(Locale.GERMAN);
        char decimalSeparator = decimalFormat != null && decimalFormat.getDecimalFormatSymbols() != null ? decimalFormat.getDecimalFormatSymbols().getDecimalSeparator() : ' ';
        s = s.replaceAll("[^\\d" + decimalSeparator + "]", "");
        assertEquals("10,01", s);
    }

    @Test
    void givenAStringWithCommaAsDecimalSeparator_whenRemovingUsingJava8Stream_thenShouldGetExpectedResult() {
        double d = 10.01d;
        String s = String.format(Locale.GERMAN, "%.2f", d);
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance(Locale.GERMAN);
        char decimalSeparator = decimalFormat != null && decimalFormat.getDecimalFormatSymbols() != null ? decimalFormat.getDecimalFormatSymbols().getDecimalSeparator() : ' ';
        s = s.replaceAll("[^\\d" + decimalSeparator + "]", "");

        StringBuilder sb = new StringBuilder();
        s.chars()
          .mapToObj(c -> (char) c)
          .filter(c -> Character.isDigit(c) || c == decimalSeparator)
          .forEach(sb::append);
        assertEquals("10,01", sb.toString());
    }

    @Test
    void givenAStringWithCommaAsDecimalSeparator_whenRemovingUsingGuavaLibrary_thenShouldGetExpectedResult() {
        double d = 10.01d;
        String s = String.format(Locale.GERMAN, "%.2f", d);
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance(Locale.GERMAN);
        char decimalSeparator = decimalFormat != null && decimalFormat.getDecimalFormatSymbols() != null ? decimalFormat.getDecimalFormatSymbols().getDecimalSeparator() : ' ';
        String result = CharMatcher.inRange('0', '9')
          .or(CharMatcher.is(decimalSeparator))
          .retainFrom(s);
        assertEquals("10,01", result);
    }

    @Test
    void givenAStringWithCommaAsDecimalSeparator_whenRemovingUsingApacheCommonsLibrary_thenShouldGetExpectedResult() {
        double d = 10.01d;
        String s = String.format(Locale.GERMAN, "%.2f", d);
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance(Locale.GERMAN);
        char decimalSeparator = decimalFormat != null && decimalFormat.getDecimalFormatSymbols() != null ? decimalFormat.getDecimalFormatSymbols().getDecimalSeparator() : ' ';
        String result = RegExUtils.removeAll(s, "[^\\d" + decimalSeparator + "]");
        assertEquals("10,01", result);
    }
}
