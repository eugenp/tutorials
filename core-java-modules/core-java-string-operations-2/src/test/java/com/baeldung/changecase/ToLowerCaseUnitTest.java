package com.baeldung.changecase;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class ToLowerCaseUnitTest {

    private static final Locale TURKISH = new Locale("tr");
    private String name = "John Doe";
    private String foreignUppercase = "\u0049";

    @Test
    public void givenMixedCaseString_WhenToLowerCase_ThenResultIsLowerCase() {
        assertEquals("john doe", name.toLowerCase());
    }

    @Test
    public void givenForeignString_WhenToLowerCaseWithoutLocale_ThenResultIsLowerCase() {
        assertEquals("\u0069", foreignUppercase.toLowerCase());
    }

    @Test
    public void givenForeignString_WhenToLowerCaseWithLocale_ThenResultIsLowerCase() {
        assertEquals("\u0131", foreignUppercase.toLowerCase(TURKISH));
    }
}
