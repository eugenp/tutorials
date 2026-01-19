package com.baeldung.map.randommapkey;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class RandomKeyShuffleMapUnitTest {

    private final RandomKeyShuffleMap randomKeyShuffleMap = new RandomKeyShuffleMap();

    @Test
    public void whenGettingRandomKeyUsingShuffle_thenKeyExistsInMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);
        map.put("date", 4);

        String randomKey = randomKeyShuffleMap.getRandomKeyUsingShuffle(map);

        assertNotNull(randomKey);
        assertTrue(map.containsKey(randomKey));
    }

    @Test
    public void whenMapIsEmpty_thenReturningNull() {
        Map<String, Integer> map = new HashMap<>();

        String randomKey = randomKeyShuffleMap.getRandomKeyUsingShuffle(map);

        assertNull(randomKey);
    }

    @Test
    public void whenMapIsNull_thenReturningNull() {
        String randomKey = randomKeyShuffleMap.getRandomKeyUsingShuffle(null);

        assertNull(randomKey);
    }
}

