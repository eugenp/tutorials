package com.baeldung.map;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LimitMaxSizeHashMapByLinkedHashMapUnitTest {

    private final int MAX_SIZE = 4;
    private HashMapWithMaxSizeLimit<Integer, String> hashMapWithMaxSizeLimit;

    @BeforeEach
    void setUp() {
        hashMapWithMaxSizeLimit = new HashMapWithMaxSizeLimit<Integer, String>(MAX_SIZE);
    }

    @Test
    void givenCustomHashMapObject_whenAddingNewEntryAndLimitExceeded_thenThrowsException() {
        Exception exception = assertThrows(Exception.class, () -> {
            hashMapWithMaxSizeLimit.putWithLimit(1, "One");
            hashMapWithMaxSizeLimit.putWithLimit(2, "Two");
            hashMapWithMaxSizeLimit.putWithLimit(3, "Three");
            hashMapWithMaxSizeLimit.putWithLimit(4, "Four");
            hashMapWithMaxSizeLimit.putWithLimit(5, "Five");
        });

        String messageThrownWhenSizeExceedsLimit = "Max size exceeded!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equals(messageThrownWhenSizeExceedsLimit));
    }

}
