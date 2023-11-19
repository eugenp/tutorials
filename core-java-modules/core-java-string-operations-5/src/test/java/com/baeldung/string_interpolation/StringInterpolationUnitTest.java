package com.baeldung.string_interpolation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.junit.jupiter.api.Test;

public class StringInterpolationUnitTest {
    private final String EXPECTED_STRING = "String Interpolation in Java with some Java examples.";

    @Test
    public void givenTwoString_thenInterpolateWithPlusSign() {
        String EXPECTED_STRING = "String Interpolation in Java with some Java examples.";
        String first = "Interpolation";
        String second = "Java";
        String result = "String " + first + " in " + second + " with some " + second + " examples.";
        assertEquals(EXPECTED_STRING, result);
    }

    @Test
    public void givenTwoString_thenInterpolateWithFormat() {
        String first = "Interpolation";
        String second = "Java";
        String result = String.format("String %s in %s with some %s examples.", first, second, second);
        assertEquals(EXPECTED_STRING, result);
    }

    @Test
    public void givenTwoString_thenInterpolateWithFormatted() {
        String first = "Interpolation";
        String second = "Java";
        String result = String.format("String %s in %s with some %s examples.", first, second, second);
        assertEquals(EXPECTED_STRING, result);
    }

    @Test
    public void givenTwoString_thenInterpolateWithFormatStringReference() {
        String first = "Interpolation";
        String second = "Java";
        String result = String.format("String %1$s in %2$s with some %2$s examples.", first, second);
        assertEquals(EXPECTED_STRING, result);
    }

    @Test
    public void givenTwoString_thenInterpolateWithStringBuilder() {
        String first = "Interpolation";
        String second = "Java";
        StringBuilder builder = new StringBuilder();
        builder.append("String ")
            .append(first)
            .append(" in ")
            .append(second)
            .append(" with some ")
            .append(second)
            .append(" examples.");
        String result = builder.toString();
        assertEquals(EXPECTED_STRING, result);
    }

    @Test
    public void givenTwoString_thenInterpolateWithMessageFormat() {
        String first = "Interpolation";
        String second = "Java";
        String result = MessageFormat.format("String {0} in {1} with some {1} examples.", first, second);
        assertEquals(EXPECTED_STRING, result);
    }

    @Test
    public void givenTwoString_thenInterpolateWithStringSubstitutor() {
        String baseString = "String ${first} in ${second} with some ${second} examples.";
        String first = "Interpolation";
        String second = "Java";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("first", first);
        parameters.put("second", second);
        StringSubstitutor substitutor = new StringSubstitutor(parameters);
        String result = substitutor.replace(baseString);
        assertEquals(EXPECTED_STRING, result);
    }
}
