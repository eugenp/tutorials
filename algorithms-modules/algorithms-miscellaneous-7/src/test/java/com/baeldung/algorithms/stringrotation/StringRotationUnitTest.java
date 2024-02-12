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
    void givenOriginAndRotationInput_whenCheckIfOriginContainsRotation_thenOk() {
        assertTrue(doubledOriginContainsRotation("abcd", "cdab"));
        assertTrue(doubledOriginContainsRotation("abcd", "abcd"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckIfOriginContainsRotation_thenKo() {
        assertFalse(doubledOriginContainsRotation("abcd", "bbbb"));
        assertFalse(doubledOriginContainsRotation("abcd", "abcde"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckingCommonStartWithOrigin_thenOk() {
        assertTrue(isRotationUsingCommonStartWithOrigin("abcd", "cdab"));
        assertTrue(isRotationUsingCommonStartWithOrigin("abcd", "abcd"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckingCommonStartWithOrigin_thenKo() {
        assertFalse(isRotationUsingCommonStartWithOrigin("abcd", "bbbb"));
        assertFalse(isRotationUsingCommonStartWithOrigin("abcd", "abcde"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckingUsingQueues_thenOk() {
        assertTrue(isRotationUsingQueue("abcd", "cdab"));
        assertTrue(isRotationUsingQueue("abcd", "abcd"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckingUsingQueues_thenKo() {
        assertFalse(isRotationUsingQueue("abcd", "bbbb"));
        assertFalse(isRotationUsingQueue("abcd", "abcde"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckingUsingSuffixAndPrefix_thenOk() {
        assertTrue(isRotationUsingSuffixAndPrefix("abcd", "cdab"));
        assertTrue(isRotationUsingSuffixAndPrefix("abcd", "abcd"));
    }

    @Test
    void givenOriginAndRotationInput_whenCheckingUsingSuffixAndPrefix_thenKo() {
        assertFalse(isRotationUsingSuffixAndPrefix("abcd", "bbbb"));
        assertFalse(isRotationUsingSuffixAndPrefix("abcd", "abcde"));
    }
}
