package com.baeldung.algorithms.fizzbuzz;

import java.util.ArrayList;
import java.util.List;

/**
 * FizzBuzz implementation demonstrating three different approaches to solve
 * the classic FizzBuzz programming puzzle.
 * <p>
 * Problem Stmt: Given an positive integer n, iterate over 1 to n, print "Fizz" for multiples of 3, "Buzz" for
 * multiples of 5, "FizzBuzz" for multiples of both, and the number otherwise.
 */
public class FizzBuzz {

    /**
     * Naive approach using explicit modulo checks with if-else chain.
     * Order of conditions is critical - must check divisibility by both 3 and 5 first.
     *
     * @param n the upper limit (inclusive)
     * @return list of FizzBuzz results
     */
    public List<String> fizzBuzzNaive(int n) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                result.add("FizzBuzz");
            } else if (i % 3 == 0) {
                result.add("Fizz");
            } else if (i % 5 == 0) {
                result.add("Buzz");
            } else {
                result.add(String.valueOf(i));
            }
        }
        return result;
    }

    /**
     * String concatenation approach that elegantly handles the FizzBuzz case.
     * Uses StringBuilder reuse with setLength(0) to avoid repeated instantiation.
     *
     * @param n the upper limit (inclusive)
     * @return list of FizzBuzz results
     * @see <a href="https://www.baeldung.com/java-clear-stringbuilder-stringbuffer">Clearing StringBuilder</a>
     */
    public List<String> fizzBuzzConcatenation(int n) {
        List<String> result = new ArrayList<>();
        StringBuilder output = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0) {
                output.append("Fizz");
            }
            if (i % 5 == 0) {
                output.append("Buzz");
            }
            result.add(output.length() > 0 ? output.toString() : String.valueOf(i));
            output.setLength(0);
        }
        return result;
    }

    /**
     * Counter approach that eliminates modulo operations using counters.
     * Uses StringBuilder reuse with setLength(0) to avoid repeated instantiation.
     *
     * @param n the upper limit (inclusive)
     * @return list of FizzBuzz results
     * @see <a href="https://www.baeldung.com/java-clear-stringbuilder-stringbuffer">Clearing StringBuilder</a>
     */
    public List<String> fizzBuzzCounter(int n) {
        List<String> result = new ArrayList<>();
        StringBuilder output = new StringBuilder();
        int fizz = 0;
        int buzz = 0;
        for (int i = 1; i <= n; i++) {
            fizz++;
            buzz++;
            if (fizz == 3) {
                output.append("Fizz");
                fizz = 0;
            }
            if (buzz == 5) {
                output.append("Buzz");
                buzz = 0;
            }
            result.add(output.length() > 0 ? output.toString() : String.valueOf(i));
            output.setLength(0);
        }
        return result;
    }
}