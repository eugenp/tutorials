package com.baeldung.interview;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringFormatUnitTest {
    @Test
    public void givenString_whenUsingStringFormat_thenStringFormatted() {
        String title = "Baeldung"; 
        String formatted = String.format("Title is %s", title);
        assertEquals(formatted, "Title is Baeldung");
    }
}
