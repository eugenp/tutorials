package com.baeldung.alphabetgeneration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class GenerationOfAlphabetsUsingForVariousWaysUnitTest {
    @Test
    void givenAForLoop_whenGeneratingAlphabets_thenAssertTrue() {
        final List<Character> allCapitalAlphabets = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));
        List<Character> alphabets = new ArrayList<>();
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            alphabets.add(alphabet);
        }
        Assertions.assertEquals(alphabets, allCapitalAlphabets);
    }

    @Test
    void givenStreams_whenGeneratingAlphabets_thenAssertTrue() {
        final List<Character> allCapitalAlphabets = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));
        final List<Character> alphabets = IntStream.rangeClosed('A', 'Z').mapToObj(c -> (char) c).collect(Collectors.toList());
        Assertions.assertEquals(alphabets, allCapitalAlphabets);
    }
}
