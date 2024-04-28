package com.baeldung.distinctcharacters;

import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class DistinctCharactersUnitTest {
    String inputString = "BBaaeelldduunngg";

    @Test
    public void givenString_whenUsingSet_thenFindDistinctCharacters() {
        Set<Character> distinctChars = new HashSet<>();
        for (char ch : inputString.toCharArray()) {
            distinctChars.add(ch);
        }
        assertEquals(Set.of('B', 'a', 'e', 'l', 'd', 'u', 'n', 'g'), distinctChars);
    }

    @Test
    public void givenString_whenUsingStreams_thenFindDistinctCharacters() {
        Set<Character> distinctChars = inputString.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
        assertEquals(Set.of('B', 'a', 'e', 'l', 'd', 'u', 'n', 'g'), distinctChars);
    }

    @Test
    public void givenString_whenUsingLinkedHashMap_thenFindDistinctCharacters() {
        Map<Character, Integer> charCount = new LinkedHashMap<>();
        for (char ch : inputString.toCharArray()) {
            charCount.put(ch, 1);
        }
        assertEquals("[B, a, e, l, d, u, n, g]", charCount.keySet().toString());
    }
}
