package com.baeldung.map;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LimitMaxSizeHashMapByCustomHashMapUnitTest {

    private final int MAX_SIZE = 4;
    private HashMapWithMaxSizeLimit<Integer, String> hashMapWithMaxSizeLimit;

    @Test
    void givenCustomHashMapObject_whenThereIsNoLimit_thenDoesNotThrowException() {
        hashMapWithMaxSizeLimit = new HashMapWithMaxSizeLimit<Integer, String>();
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 10000; i++) {
                hashMapWithMaxSizeLimit.put(i, i + "");
            }
        });
    }

    @Test
    void givenCustomHashMapObject_whenLimitNotReached_thenDoesNotThrowException() {
        hashMapWithMaxSizeLimit = new HashMapWithMaxSizeLimit<Integer, String>(MAX_SIZE);
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 4; i++) {
                hashMapWithMaxSizeLimit.put(i, i + "");
            }
        });
    }

    @Test
    void givenCustomHashMapObject_whenReplacingValueWhenLimitIsReached_thenDoesNotThrowException() {
        hashMapWithMaxSizeLimit = new HashMapWithMaxSizeLimit<Integer, String>(MAX_SIZE);
        assertDoesNotThrow(() -> {
            hashMapWithMaxSizeLimit.put(1, "One");
            hashMapWithMaxSizeLimit.put(2, "Two");
            hashMapWithMaxSizeLimit.put(3, "Three");
            hashMapWithMaxSizeLimit.put(4, "Four");
            hashMapWithMaxSizeLimit.put(4, "4");
        });
    }

    @Test
    void givenCustomHashMapObject_whenLimitExceeded_thenThrowsException() {
        hashMapWithMaxSizeLimit = new HashMapWithMaxSizeLimit<Integer, String>(MAX_SIZE);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            for (int i = 0; i < 5; i++) {
                hashMapWithMaxSizeLimit.put(i, i + "");
            }
        });

        String messageThrownWhenSizeExceedsLimit = "Max size exceeded!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equals(messageThrownWhenSizeExceedsLimit));
    }

}
