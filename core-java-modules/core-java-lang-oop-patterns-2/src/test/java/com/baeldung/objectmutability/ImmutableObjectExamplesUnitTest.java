package com.baeldung.objectmutability;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class ImmutableObjectExamplesUnitTest {

    @Test
    public void givenImmutableString_whenConcatString_thenNotSameAndCorrectValues() {
        String originalString = "Hello";
        String modifiedString = originalString.concat(" World");

        assertNotSame(originalString, modifiedString);

        assertEquals("Hello", originalString);
        assertEquals("Hello World", modifiedString);
    }

    @Test
    public void givenImmutableInteger_whenAddInteger_thenNotSameAndCorrectValue() {
        Integer immutableInt = 42;
        Integer modifiedInt = immutableInt + 8;

        assertNotSame(immutableInt, modifiedInt);

        assertEquals(42, (int) immutableInt);
        assertEquals(50, (int) modifiedInt);
    }
}


