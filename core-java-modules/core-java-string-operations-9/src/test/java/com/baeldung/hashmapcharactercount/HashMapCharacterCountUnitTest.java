package com.baeldung.hashmapcharactercount;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;

public class HashMapCharacterCountUnitTest {
    String str = "abcaadcbcb";

    @Test
    public void givenString_whenUsingStreams_thenVerifyCounts() {
        Map<Character, Integer> charCount = str.chars()
                .boxed()
                .collect(toMap(
                        k -> (char) k.intValue(),
                        v -> 1,
                        Integer::sum));

        assertEquals(3, charCount.get('a').intValue());
    }

    @Test
    public void givenString_whenUsingLooping_thenVerifyCounts() {
        Map<Character, Integer> charCount = new HashMap<>();
        for (char c : str.toCharArray()) {
            charCount.merge(c,
                    1,
                    Integer::sum);
        }
        assertEquals(3, charCount.get('a').intValue());
    }


}
