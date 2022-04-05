package com.baeldung.java9.compactstring;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class CompactStringDemo {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List strings = IntStream.rangeClosed(1, 10_000_000)
          .mapToObj(Integer::toString).collect(toList());
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Generated " + strings.size() + " strings in "
          + totalTime + " ms.");

        startTime = System.currentTimeMillis();
        String appended = (String) strings.stream().limit(100_000)
          .reduce("", (left, right) -> left.toString() + right.toString());
        totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Created string of length " + appended.length()
          + " in " + totalTime + " ms.");
    }
}
