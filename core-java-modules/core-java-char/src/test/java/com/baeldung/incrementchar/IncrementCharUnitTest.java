package com.baeldung.incrementchar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class IncrementCharUnitTest {
    @Test
    void whenUsingForLoop_thenGenerateCharacters(){
        final List<Character> allCapitalCharacters = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
        List<Character> characters = new ArrayList<>();
        for (char character = 'A'; character <= 'Z'; character++) {
            characters.add(character);
        }
        Assertions.assertEquals(characters, allCapitalCharacters);
    }

    @Test
    void whenUsingStreams_thenGenerateCharacters() {
        final List<Character> allCapitalCharacters = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
        final List<Character> characters = IntStream.rangeClosed('A', 'Z').mapToObj(c -> (char) c).collect(Collectors.toList());
        Assertions.assertEquals(characters, allCapitalCharacters);
    }
}
