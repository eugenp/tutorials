package com.baeldung.string.interview;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringChangeCaseUnitTest {
    @Test
    public void givenString_whenChangingToUppercase_thenCaseChanged() {
        String s = "Welcome to Baeldung!";
        assertEquals("WELCOME TO BAELDUNG!", s.toUpperCase());
    }
    

    @Test
    public void givenString_whenChangingToLowerrcase_thenCaseChanged() {
        String s = "Welcome to Baeldung!";
        assertEquals("welcome to baeldung!", s.toLowerCase());
    }
}
