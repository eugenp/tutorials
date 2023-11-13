package com.baeldung.map;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LimitMaxSizeHashMapByCustomHashMapUnitTest {

    private final int MAX_SIZE = 4;
    private HashMapWithMaxSizeLimit<Integer, String> hashMapWithMaxSizeLimit;

    @BeforeEach
    void setUp() {
        hashMapWithMaxSizeLimit = new HashMapWithMaxSizeLimit<Integer, String>(MAX_SIZE);
    }

    @Test
    void givenCustomHashMapObject_whenAddingNewEntryAndLimitExceeded_thenThrowsException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            hashMapWithMaxSizeLimit.put(1, "One");
            hashMapWithMaxSizeLimit.put(2, "Two");
            hashMapWithMaxSizeLimit.put(3, "Three");
            hashMapWithMaxSizeLimit.put(4, "Four");
            hashMapWithMaxSizeLimit.put(5, "Five");
        });

        String messageThrownWhenSizeExceedsLimit = "Max size exceeded!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equals(messageThrownWhenSizeExceedsLimit));
    }

}
