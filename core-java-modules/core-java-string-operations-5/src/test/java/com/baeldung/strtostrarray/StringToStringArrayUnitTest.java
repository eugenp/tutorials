package com.baeldung.strtostrarray;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class StringToStringArrayUnitTest {
    private static final String INPUT = "Hi there, nice to meet you!";

    @Test
    void givenAString_whenConvertToSingletonArray_shouldGetExpectedResult() {
        String[] myArray = new String[] { INPUT };
        assertArrayEquals(new String[] { "Hi there, nice to meet you!" }, myArray);
    }

    @Test
    void givenAString_whenSplitToWordArray_shouldGetExpectedResult() {
        String[] myArray = INPUT.split("\\W+");
        assertArrayEquals(new String[] { "Hi", "there", "nice", "to", "meet", "you" }, myArray);
    }

    @Test
    void givenAString_whenSplitToSentenceArray_shouldGetExpectedResult() {
        String[] myArray = INPUT.split("[-,.!;?]\\s*" );
        assertArrayEquals(new String[] { "Hi there", "nice to meet you" }, myArray);
    }

    @Test
    void givenAString_whenSplitToCharArray_shouldGetExpectedResult() {
        String[] myArray = INPUT.split("");
        assertArrayEquals(new String[] {
            "H", "i", " ", "t", "h", "e", "r", "e", ",", " ",
            "n", "i", "c", "e", " ", "t", "o", " ", "m", "e", "e", "t", " ", "y", "o", "u", "!"
        }, myArray);
    }

}
