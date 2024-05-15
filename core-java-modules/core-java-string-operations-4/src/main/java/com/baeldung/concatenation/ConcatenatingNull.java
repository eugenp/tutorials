package com.baeldung.concatenation;

import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConcatenatingNull {

    public static void main(String[] args) {
        String[] values = { "Java ", null, "", "is ", "great!" };

        concatenateUsingPlusOperator(values);
        concatenateUsingHelperMethod(values);
        concatenateUsingStringBuilder(values);
        concatenateUsingJoin(values);
        concatenateUsingStringJoiner(values);
        concatenateUsingCollectorsJoining(values);
        concatenateUsingStringConcat(values);
    }

    public static String concatenateUsingStringConcat(String[] values) {
        String result = "";

        for (String value : values) {
            result = result.concat(getNonNullString(value));
        }

        return result;
    }

    public static String concatenateUsingCollectorsJoining(String[] values) {
        String result = Stream.of(values).filter(value -> null != value).collect(Collectors.joining(""));

        return result;
    }

    public static String concatenateUsingStringJoiner(String[] values) {
        StringJoiner result = new StringJoiner("");

        for (String value : values) {
            result = result.add(getNonNullString(value));
        }

        return result.toString();
    }

    public static String concatenateUsingJoin(String[] values) {
        String result = String.join("", values);

        return result;
    }

    public static String concatenateUsingStringBuilder(String[] values) {
        StringBuilder result = new StringBuilder();

        for (String value : values) {
            result = result.append(getNonNullString(value));
        }

        return result.toString();
    }

    public static String concatenateUsingHelperMethod(String[] values) {
        String result = "";

        for (String value : values) {
            result = result + getNonNullString(value);
        }

        return result;
    }

    public static String concatenateUsingPlusOperator(String[] values) {
        String result = "";

        for (String value : values) {
            result = result + (value == null ? "" : value);
        }

        return result;
    }

    private static String getNonNullString(String value) {
        return value == null ? "" : value;
    }
}
