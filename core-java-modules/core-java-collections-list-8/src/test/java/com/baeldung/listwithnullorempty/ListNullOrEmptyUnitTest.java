package com.baeldung.listwithnullorempty;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ListNullOrEmptyUnitTest {

    List<String> list = Arrays.asList("Madrid", null, "  ", "Havana", "");

    @Test
    void givenListWithNullOrEmpty_whenCheckForNullOrEmptyUsingForLoop_thenReturnTrue() {
        boolean hasNullOrEmpty = false;
        for (String s : list) {
            if (s == null || s.isEmpty()) {
                hasNullOrEmpty = true;
                break;
            }
        }

        assertTrue(hasNullOrEmpty, "List should contain null or empty elements");
    }

    @Test
    void givenListWithNullOrEmpty_whenCheckForNullOrEmptyUsingStreams_thenReturnTrue() {
        boolean hasNullOrEmpty = list.stream()
          .anyMatch(s -> s == null || s.isEmpty());

        assertTrue(hasNullOrEmpty, "List should contain null or blank elements");
    }

    @Test
    void givenListWithNullOrEmpty_whenCheckUsingParallelStream_thenReturnTrue() {
        boolean hasNullOrEmpty = list.parallelStream()
                .anyMatch(s -> s == null || s.isEmpty());

        assertTrue(hasNullOrEmpty, "List should contain null or empty elements");
    }

}
