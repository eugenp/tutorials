package com.baeldung.algorithms.fizzbuzz;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit 5 test suite for FizzBuzz implementation.
 * Tests all three approaches: Naive, Concatenation, and Counter.
 * <p></p>
 * Test naming convention: givenWW_whenYY_thenXX
 */
class FizzBuzzUnitTest {

    private FizzBuzz fizzBuzz;

    private static final List<String> GROUND_TRUTH_N5 = List.of(
        "1", "2", "Fizz", "4", "Buzz"
    );

    private static final List<String> GROUND_TRUTH_N15 = List.of(
        "1", "2", "Fizz", "4", "Buzz",
        "Fizz", "7", "8", "Fizz", "Buzz",
        "11", "Fizz", "13", "14", "FizzBuzz"
    );

    private static final List<String> GROUND_TRUTH_N100 = generateGroundTruth(100);

    @BeforeEach
    void setUp() {
        fizzBuzz = new FizzBuzz();
    }

    @Test
    void givenNLessThan15_whenAllMethods_thenReturnCorrectSequence() {
        List<String> naiveResult = fizzBuzz.fizzBuzzNaive(5);
        List<String> concatResult = fizzBuzz.fizzBuzzConcatenation(5);
        List<String> counterResult = fizzBuzz.fizzBuzzCounter(5);

        assertAll(
            () -> assertEquals(GROUND_TRUTH_N5, naiveResult,
                "fizzBuzzNaive should return correct sequence for n=5"),
            () -> assertEquals(GROUND_TRUTH_N5, concatResult,
                "fizzBuzzConcatenation should return correct sequence for n=5"),
            () -> assertEquals(GROUND_TRUTH_N5, counterResult,
                "fizzBuzzOptimized should return correct sequence for n=5")
        );
    }

    @Test
    void givenN100_whenAllMethods_thenReturnCorrectSequence() {
        List<String> naiveResult = fizzBuzz.fizzBuzzNaive(100);
        List<String> concatResult = fizzBuzz.fizzBuzzConcatenation(100);
        List<String> counterResult = fizzBuzz.fizzBuzzCounter(100);

        assertAll(
            () -> assertEquals(GROUND_TRUTH_N100, naiveResult,
                "fizzBuzzNaive should return correct sequence for n=100"),
            () -> assertEquals(GROUND_TRUTH_N100, concatResult,
                "fizzBuzzConcatenation should return correct sequence for n=100"),
            () -> assertEquals(GROUND_TRUTH_N100, counterResult,
                "fizzBuzzOptimized should return correct sequence for n=100")
        );
    }

    private static List<String> generateGroundTruth(int n) {
        return IntStream.rangeClosed(1, n)
            .mapToObj(i -> {
                if (i % 15 == 0) return "FizzBuzz";
                if (i % 3 == 0) return "Fizz";
                if (i % 5 == 0) return "Buzz";
                return String.valueOf(i);
            })
            .collect(Collectors.toList());
    }
}
