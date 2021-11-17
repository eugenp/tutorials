package com.baeldung.concatenation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConcatenatingNull {

    public static void main(String[] args) {
        String[] values = { "Baeldung ", null, "is ", "awesome!" };

        concatenateUsingPlusOperator(values);
        concatenateUsingHelperMethod(values);
        concatenateUsingStringBuilder(values);
        concatenateUsingJoin(values);
        concatenateUsingStringJoiner(values);
        concatenateUsingCollectorsJoining(values);
        concatenateUsingStringConcat(values);
    }

    private static void concatenateUsingStringConcat(String[] values) {
        String result = "";
        for (String value : values) {
            result = result.concat(getNonNullString(value));
        }

        assertEquals("Baeldung is awesome!", result);
    }

    private static void concatenateUsingCollectorsJoining(String[] values) {
        String result = Stream.of(values).filter(value -> null != value).collect(Collectors.joining(""));

        assertEquals("Baeldung is awesome!", result.toString());
    }

    private static void concatenateUsingStringJoiner(String[] values) {
        StringJoiner result = new StringJoiner("");
        for (String value : values) {
            result = result.add(getNonNullString(value));
        }

        assertEquals("Baeldung is awesome!", result.toString());
    }

    private static void concatenateUsingJoin(String[] values) {
        String result = String.join("", values);
        
        assertEquals("Baeldung nullis awesome!", result);
    }

    private static void concatenateUsingStringBuilder(String[] values) {
        StringBuilder result = new StringBuilder();
        for (String value : values) {
            result = result.append(getNonNullString(value));
        }

        assertEquals("Baeldung is awesome!", result.toString());
    }

    private static void concatenateUsingHelperMethod(String[] values) {
        String result = "";
        for (String value : values) {
            result = result + getNonNullString(value);
        }

        assertEquals("Baeldung is awesome!", result);
    }

    private static void concatenateUsingPlusOperator(String... values) {
        String result = "";
        for (String value : values) {
            result = result + getNonNullString(value);
        }

        assertEquals("Baeldung is awesome!", result);
    }

    private static String getNonNullString(String value) {
        return value == null ? "" : value;
    }
}
