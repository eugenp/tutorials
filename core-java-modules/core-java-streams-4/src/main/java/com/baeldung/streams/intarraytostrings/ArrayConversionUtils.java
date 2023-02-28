package com.baeldung.streams.intarraytostrings;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayConversionUtils {

    public static void createStreamExample() {
        int[] intArray = { 1, 2, 3, 4, 5 };
        IntStream intStream = Arrays.stream(intArray);

        Integer[] integerArray = { 1, 2, 3, 4, 5 };
        Stream<Integer> integerStream = Arrays.stream(integerArray);
    }

    public static String[] convertToStringArray(Integer[] input) {
        return Arrays.stream(input)
          .map(Object::toString)
          .toArray(String[]::new);
    }

    public static String[] convertToStringArray(int[] input) {
        return Arrays.stream(input)
          .mapToObj(Integer::toString)
          .toArray(String[]::new);
    }

    public static String[] convertToStringArrayWithBoxing(int[] input) {
        return Arrays.stream(input)
          .boxed()
          .map(Object::toString)
          .toArray(String[]::new);
    }

    public static String convertToString(int[] input){
        return Arrays.stream(input)
          .mapToObj(Integer::toString)
          .collect(Collectors.joining(", "));
    }

}
