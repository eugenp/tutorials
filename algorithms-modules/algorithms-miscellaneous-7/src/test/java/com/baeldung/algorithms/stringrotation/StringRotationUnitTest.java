package com.baeldung.algorithms.stringrotation;

import static com.baeldung.algorithms.stringrotation.StringRotation.doubledOriginContainsRotation;
import static com.baeldung.algorithms.stringrotation.StringRotation.isRotationUsingCommonStartWithOrigin;
import static com.baeldung.algorithms.stringrotation.StringRotation.isRotationUsingQueue;
import static com.baeldung.algorithms.stringrotation.StringRotation.isRotationUsingSuffixAndPrefix;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringRotationUnitTest {

    @Test
    void givenOriginAndRotationInput_whenCheckIfOriginContainsRotation_thenIsRotation() {
        assertTrue(doubledOriginContainsRotation("abcd", "cdab"));
        assertTrue(doubledOriginContainsRotation("abcd", "abcd"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckIfOriginContainsRotation_thenNoRotation() {
        assertFalse(doubledOriginContainsRotation("abcd", "bbbb"));
        assertFalse(doubledOriginContainsRotation("abcd", "abcde"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckingCommonStartWithOrigin_thenIsRotation() {
        assertTrue(isRotationUsingCommonStartWithOrigin("abcd", "cdab"));
        assertTrue(isRotationUsingCommonStartWithOrigin("abcd", "abcd"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckingCommonStartWithOrigin_thenNoRotation() {
        assertFalse(isRotationUsingCommonStartWithOrigin("abcd", "bbbb"));
        assertFalse(isRotationUsingCommonStartWithOrigin("abcd", "abcde"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckingUsingQueues_thenIsRotation() {
        assertTrue(isRotationUsingQueue("abcd", "cdab"));
        assertTrue(isRotationUsingQueue("abcd", "abcd"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckingUsingQueues_thenNoRotation() {
        assertFalse(isRotationUsingQueue("abcd", "bbbb"));
        assertFalse(isRotationUsingQueue("abcd", "abcde"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckingUsingSuffixAndPrefix_thenIsRotation() {
        assertTrue(isRotationUsingSuffixAndPrefix("abcd", "cdab"));
        assertTrue(isRotationUsingSuffixAndPrefix("abcd", "abcd"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckingUsingSuffixAndPrefix_thenNoRotation() {
        assertFalse(isRotationUsingSuffixAndPrefix("abcd", "bbbb"));
        assertFalse(isRotationUsingSuffixAndPrefix("abcd", "abcde"));
    }
}
