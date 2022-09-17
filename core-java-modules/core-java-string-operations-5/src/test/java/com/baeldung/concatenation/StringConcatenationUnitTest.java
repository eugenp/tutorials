package com.baeldung.concatenation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class StringConcatenationUnitTest {

    @Test
    void whenUsingStringBuilder_thenAssertEquals() {
        StringBuilder builderOne = new StringBuilder("Hello");
        StringBuilder builderTwo = new StringBuilder(" World");
        StringBuilder builder = builderOne.append(builderTwo);
        assertEquals("Hello World", builder.toString());

    }

    @Test
    void whenUsingStringBuffer_thenAssertEquals() {
        StringBuffer buffer = new StringBuffer("Hello");
        buffer.append(" World");
        assertEquals("Hello World", buffer.toString());
    }

    @Test
    void whenUsingStringFormat_thenAssertEquals() {
        String stringOne = "Hello";
        String stringTwo = " World";
        assertEquals("Hello World", String.format("%s%s", stringOne, stringTwo));
    }

    @Test
    void whenUsingStringJoin_thenAssertEquals() {
        String stringOne = "Hello";
        String stringTwo = " World";
        assertEquals("Hello World", String.join("", stringOne, stringTwo));
    }

    @Test
    void whenUsingStringJoiner_thenAssertEquals() {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add("Hello");
        joiner.add("World");
        assertEquals("Hello, World", joiner.toString());
    }

    @Test
    void whenUsingCollectors_thenAssertEquals() {
        List<String> words = Arrays.asList("Hello", "World");
        String collect = words.stream()
            .collect(Collectors.joining(", "));
        assertEquals("Hello, World", collect);
    }

    @Test
    void whenUsingConcat_thenAssertEquals() {
        String stringOne = "Hello";
        String stringTwo = " World";
        assertEquals("Hello World", stringOne.concat(stringTwo));
    }

    @Test
    void whenUsingConcatWithOutAssignment_thenAssertNotEquals() {
        String stringOne = "Hello";
        String stringTwo = " World";
        stringOne.concat(stringTwo);
        assertNotEquals("Hello World", stringOne);
    }

    @Test
    void whenUsingConcatWithAssignment_thenAssertEquals() {
        String stringOne = "Hello";
        String stringTwo = " World";
        stringOne = stringOne.concat(stringTwo);
        assertEquals("Hello World", stringOne);
    }

    @Test
    void whenUsingConcatToMultipleStringConcatenation_thenAssertEquals() {
        String stringOne = "Hello";
        String stringTwo = "World";
        String stringThree = ", in Jav";
        stringOne = stringOne.concat(" ")
            .concat(stringTwo)
            .concat(stringThree)
            .concat("@");
        assertEquals("Hello World, in Jav@", stringOne);
    }

    @Test
    void whenUsingConcatAppendANull_thenAssertEquals() {
        String stringOne = "Hello";
        String stringTwo = null;
        assertThrows(NullPointerException.class, () -> stringOne.concat(stringTwo));
    }

    @Test
    void whenUsingPlusOperatorANull_thenAssertEquals() {
        String stringOne = "Hello ";
        String stringTwo = null;
        assertEquals("Hello null", stringOne + stringTwo);
    }
}
