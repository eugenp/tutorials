package com.baeldung.algorithms.jugglersequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class JugglerSequenceGenerator {

    public static List<Integer> byLoop(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("The initial integer must be greater than zero.");
        }
        List<Integer> seq = new ArrayList<>();
        int current = n;
        seq.add(current);
        while (current != 1) {
            int next = (int) (Math.sqrt(current) * (current % 2 == 0 ? 1 : current));
            seq.add(next);
            current = next;
        }
        return seq;
    }

    public static List<Integer> byRecursion(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("The initial integer must be greater than zero.");
        }
        List<Integer> seq = new ArrayList<>();
        fillSeqRecursively(n, seq);
        return seq;
    }

    private static void fillSeqRecursively(int current, List<Integer> result) {
        result.add(current);
        if (current == 1) {
            return;
        }
        int next = (int) (Math.sqrt(current) * (current % 2 == 0 ? 1 : current));
        fillSeqRecursively(next, result);
    }
}

public class JugglerSequenceUnitTest {

    @Test
    void whenGeneratingJugglerSeqUsingLoop_thenGetTheExpectedResult() {
        assertThrows(IllegalArgumentException.class, () -> JugglerSequenceGenerator.byLoop(0));
        assertEquals(List.of(3, 5, 11, 36, 6, 2, 1), JugglerSequenceGenerator.byLoop(3));
        assertEquals(List.of(4, 2, 1), JugglerSequenceGenerator.byLoop(4));
        assertEquals(List.of(9, 27, 140, 11, 36, 6, 2, 1), JugglerSequenceGenerator.byLoop(9));
        assertEquals(List.of(21, 96, 9, 27, 140, 11, 36, 6, 2, 1), JugglerSequenceGenerator.byLoop(21));
        assertEquals(List.of(42, 6, 2, 1), JugglerSequenceGenerator.byLoop(42));
    }

    @Test
    void whenGeneratingJugglerSeqUsingRecursion_thenGetTheExpectedResult() {
        assertThrows(IllegalArgumentException.class, () -> JugglerSequenceGenerator.byRecursion(0));
        assertEquals(List.of(3, 5, 11, 36, 6, 2, 1), JugglerSequenceGenerator.byRecursion(3));
        assertEquals(List.of(4, 2, 1), JugglerSequenceGenerator.byRecursion(4));
        assertEquals(List.of(9, 27, 140, 11, 36, 6, 2, 1), JugglerSequenceGenerator.byRecursion(9));
        assertEquals(List.of(21, 96, 9, 27, 140, 11, 36, 6, 2, 1), JugglerSequenceGenerator.byRecursion(21));
        assertEquals(List.of(42, 6, 2, 1), JugglerSequenceGenerator.byRecursion(42));
    }
}