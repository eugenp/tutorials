package com.baeldung.strtostrarray;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class StringToStringArrayUnitTest {
    private static final String INPUT = "Hi there, nice to meet you!";
    private static final String FLIGHT_INPUT = "20221018LH720FRAPEK";

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
        String[] myArray = INPUT.split("[-,.!;?]\\s*");
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

    @Test
    void givenAString_whenSpecialRuleRequired_shouldGetExpectedResult() {
        String dateStr = FLIGHT_INPUT.substring(0, 8);
        String flightNo = FLIGHT_INPUT.substring(8, FLIGHT_INPUT.length() - 6);
        int airportStart = dateStr.length() + flightNo.length();
        String from = FLIGHT_INPUT.substring(airportStart, airportStart + 3);
        String to = FLIGHT_INPUT.substring(airportStart + 3);

        String[] myArray = new String[] { dateStr, flightNo, from, to };
        assertArrayEquals(new String[] { "20221018", "LH720", "FRA", "PEK" }, myArray);
    }
}
