package com.baeldung.countingchars;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CharacterFrequencyCounterUnitTest {

    @Test
    public void givenSimpleInput_whenCountingCharactersWithLoop_thenReturnsCorrectFrequencies() {
        String input = "test";
        Map<Character, Integer> result = CharacterFrequencyCounter.countCharactersWithLoop(input);

        assertEquals(Integer.valueOf(2), result.get('t'));
        assertEquals(Integer.valueOf(1), result.get('e'));
        assertEquals(Integer.valueOf(1), result.get('s'));
        assertEquals(3, result.size());
    }

    @Test
    public void givenSimpleInput_whenCountingCharactersWithStreams_thenReturnsCorrectFrequencies() {
        String input = "test";
        Map<Character, Integer> result = CharacterFrequencyCounter.countCharactersWithStreams(input);

        assertEquals(Integer.valueOf(2), result.get('t'));
        assertEquals(Integer.valueOf(1), result.get('e'));
        assertEquals(Integer.valueOf(1), result.get('s'));
        assertEquals(3, result.size());
    }


}
