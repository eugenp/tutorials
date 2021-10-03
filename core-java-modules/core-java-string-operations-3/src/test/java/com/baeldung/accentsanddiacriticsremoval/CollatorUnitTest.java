package com.baeldung.accentsanddiacriticsremoval;

import org.junit.Test;
import org.openjdk.jmh.annotations.Setup;

import java.text.Collator;

import static java.lang.Character.*;
import static java.lang.String.valueOf;
import static org.junit.Assert.assertEquals;

public class CollatorUnitTest {

    private final Collator collator = Collator.getInstance();

    @Setup
    public void setup() {
        collator.setDecomposition(2);
    }

    @Test
    public void givenAccentedStringAndPrimaryCollatorStrength_whenCompareWithASCIIString_thenReturnTrue() {
        Collator collator = Collator.getInstance();
        collator.setDecomposition(2);
        collator.setStrength(0);
        assertEquals(0, (collator.compare("a", "a")));
        assertEquals(0, (collator.compare("ä", "a")));
        assertEquals(0, (collator.compare("A", "a")));
        assertEquals(1, (collator.compare("b", "a")));
        assertEquals(0, (collator.compare(valueOf(toChars(0x0001)), valueOf(toChars(0x0002)))));
    }

    @Test
    public void givenAccentedStringAndSecondaryCollatorStrength_whenCompareWithASCIIString_thenReturnTrue() {
        collator.setStrength(1);
        assertEquals(1, (collator.compare("ä", "a")));
        assertEquals(1, (collator.compare("b", "a")));
        assertEquals(0, (collator.compare("A", "a")));
        assertEquals(0, (collator.compare("a", "a")));
        assertEquals(0, (collator.compare(valueOf(toChars(0x0001)), valueOf(toChars(0x0002)))));

    }

    @Test
    public void givenAccentedStringAndTeriaryCollatorStrength_whenCompareWithASCIIString_thenReturnTrue() {
        collator.setStrength(2);
        assertEquals(1, (collator.compare("A", "a")));
        assertEquals(1, (collator.compare("ä", "a")));
        assertEquals(1, (collator.compare("b", "a")));
        assertEquals(0, (collator.compare("a", "a")));
        assertEquals(0, (collator.compare(valueOf(toChars(0x0001)), valueOf(toChars(0x0002)))));
    }

    @Test
    public void givenAccentedStringAndIdenticalCollatorStrength_whenCompareWithASCIIString_thenReturnTrue() {
        collator.setStrength(3);
        assertEquals(1, (collator.compare("A", "a")));
        assertEquals(1, (collator.compare("ä", "a")));
        assertEquals(1, (collator.compare("b", "a")));
        assertEquals(-1, (collator.compare(valueOf(toChars(0x0001)), valueOf(toChars(0x0002)))));
        assertEquals(0, (collator.compare("a", "a")));
    }

    @Test
    public void givenNondecomposableAccentedStringAndIdenticalCollatorStrength_whenCompareWithASCIIString_thenReturnTrue() {
        collator.setStrength(0);
        assertEquals(1, collator.compare("ł", "l"));
        assertEquals(1, collator.compare("ø", "o"));
    }
}
