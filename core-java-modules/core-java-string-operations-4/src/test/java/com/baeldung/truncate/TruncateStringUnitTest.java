package com.baeldung.truncate;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class TruncateStringUnitTest {

    private static final String TEXT = "Welcome to baeldung.com";

    @Test
    public void givenStringAndLength_whenUsingSubstringMethod_thenTruncate() {

        assertEquals(TruncateString.usingSubstringMethod(TEXT, 10), "Welcome to");
    }

    @Test
    public void givenStringAndLength_whenUsingSplitMethod_thenTruncate() {

        assertEquals(TruncateString.usingSplitMethod(TEXT, 13), "Welcome to ba");
    }

    @Test
    public void givenStringAndLength_whenUsingPattern_thenTruncate() {

        assertEquals(TruncateString.usingPattern(TEXT, 19), "Welcome to baeldung");
    }

    @Test
    public void givenStringAndLength_whenUsingCodePointsMethod_thenTruncate() {

        assertEquals(TruncateString.usingCodePointsMethod(TEXT, 6), "Welcom");
    }

    @Test
    public void givenStringAndLength_whenUsingLeftMethod_thenTruncate() {

        assertEquals(TruncateString.usingLeftMethod(TEXT, 15), "Welcome to bael");
    }

    @Test
    public void givenStringAndLength_whenUsingTruncateMethod_thenTruncate() {

        assertEquals(TruncateString.usingTruncateMethod(TEXT, 20), "Welcome to baeldung.");
    }

    @Test
    public void givenStringAndLength_whenUsingSplitter_thenTruncate() {

        assertEquals(TruncateString.usingSplitter(TEXT, 3), "Wel");
    }

}
