package com.baeldung.string.interview;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringFormatUnitTest {
    @Test
    public void givenString_whenUsingStringFormat_thenStringFormatted() {
        String title = "Baeldung"; 
        String formatted = String.format("Title is %s", title);
        assertEquals(formatted, "Title is Baeldung");
    }
}
