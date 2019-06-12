package com.baeldung.folding;

import java.util.stream.IntStream;

public class Main {
    public static void main(String... arg) {
        final String key = "Java language";

        toAsciiCodes(key).forEach(c -> System.out.println(c));
        // toAsciiCodes(key).reduce(new ArrayList<Integer>(), (accum, i) -> i);
        // List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        // numbers.stream()
        // .reduce(new ArrayList<Integer>(), (k, v) -> new ArrayList<Integer>());
        // System.out.println(result);
    }

    public static IntStream toAsciiCodes(String key) {
        return key.chars();
    }

}
