package com.baeldung.string.changecase;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

public class ToUpperCaseUnitTest {

    private static final Locale TURKISH = new Locale("tr");
    private String name = "John Doe";
    private String foreignUppercase = "\u0069";

    @Test
    public void givenMixedCaseString_WhenToUpperCase_ThenResultIsUpperCase() {
        assertEquals("JOHN DOE", name.toUpperCase());
    }

    @Test
    public void givenForeignString_WhenToUpperCaseWithoutLocale_ThenResultIsUpperCase() {
        assertEquals("\u0049", foreignUppercase.toUpperCase());
    }

    @Test
    public void givenForeignString_WhenToUpperCaseWithLocale_ThenResultIsUpperCase() {
        assertEquals("\u0130", foreignUppercase.toUpperCase(TURKISH));
    }
}
