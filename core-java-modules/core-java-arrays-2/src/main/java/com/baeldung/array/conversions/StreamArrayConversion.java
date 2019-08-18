package com.baeldung.array.conversions;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamArrayConversion {

    public static String[] stringStreamToStringArrayUsingFunctionalInterface(Stream<String> stringStream) {
        IntFunction<String[]> intFunction = new IntFunction<String[]>() {
            @Override
            public String[] apply(int value) {
                return new String[value];
            }
        };

        return stringStream.toArray(intFunction);
    }

    public static String[] stringStreamToStringArrayUsingMethodReference(Stream<String> stringStream) {
        return stringStream.toArray(String[]::new);
    }

    public static String[] stringStreamToStringArrayUsingLambda(Stream<String> stringStream) {
        return stringStream.toArray(value -> new String[value]);
    }

    public static Integer[] integerStreamToIntegerArray(Stream<Integer> integerStream) {
        return integerStream.toArray(Integer[]::new);
    }

    public static int[] intStreamToPrimitiveIntArray(Stream<Integer> integerStream) {
        return integerStream.mapToInt(i -> i).toArray();
    }

    public static Stream<String> stringArrayToStreamUsingArraysStream(String[] stringArray) {
        return Arrays.stream(stringArray);
    }

    public static Stream<String> stringArrayToStreamUsingStreamOf(String[] stringArray) {
        return Stream.of(stringArray);
    }

    public static IntStream primitiveIntArrayToStreamUsingArraysStream(int[] intArray) {
        return Arrays.stream(intArray);
    }

    public static Stream<int[]> primitiveIntArrayToStreamUsingStreamOf(int[] intArray) {
        return Stream.of(intArray);
    }
}
