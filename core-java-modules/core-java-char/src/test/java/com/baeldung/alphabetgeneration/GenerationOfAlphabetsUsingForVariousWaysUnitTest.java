package com.baeldung.alphabetgeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Project Name: tutorials
 * @author Zahid Khan
 * @version 7/23/2023
 */
class GenerationOfAlphabetsUsingForVariousWaysUnitTest {
    static List<Character> allCapitalAlphabets;

    @BeforeAll
    static void setUp() {
        final char[] strings = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        allCapitalAlphabets = new ArrayList<>();

        for (char c : strings) {
            allCapitalAlphabets.add(c);
        }
    }

    @Test
    void givenAForLoop_whenGeneratingAlphabets_thenAssertTrue() {
        List<Character> alphabets = new ArrayList<>();
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            alphabets.add(alphabet);
        }
        Assertions.assertEquals(alphabets, allCapitalAlphabets);
    }

    @Test
    void givenStreams_whenGeneratingAlphabets_thenAssertTrue() {
        final List<Character> alphabets = IntStream.rangeClosed('A', 'Z')
            .mapToObj(c -> (char) c)
            .collect(Collectors.toList());
        Assertions.assertEquals(alphabets, allCapitalAlphabets);
    }
}
