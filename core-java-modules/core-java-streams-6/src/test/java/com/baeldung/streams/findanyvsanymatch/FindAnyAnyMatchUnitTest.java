package com.baeldung.streams.findanyvsanymatch;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindAnyAnyMatchUnitTest {

    @Test
    public void whenFilterStreamUsingFindAny_thenOK() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer result = numbers.stream()
          .filter(n -> n % 2 == 0)
          .findAny()
          .orElse(null);

        assertNotNull(result);
        assertTrue(Arrays.asList(2, 4, 6, 8, 10).contains(result));
    }

    @Test
    public void whenParallelStreamUsingFindAny_thenOK() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer result = numbers.parallelStream()
          .filter(n -> n % 2 == 0)
          .findAny()
          .orElse(null);

        assertNotNull(result);
        assertTrue(Arrays.asList(2, 4, 6, 8, 10).contains(result));
    }


    @Test
    public void whenFilterStreamUsingAnyMatch_thenOK() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        boolean result = numbers.parallelStream()
          .anyMatch(n -> n % 2 == 0);

        assertTrue(result);
    }

    @Test
    public void whenFilterStreamUsingFindFirst_thenOK() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer result = numbers.stream()
          .filter(n -> n % 2 == 0)
          .findFirst()
          .orElse(null);

        assertNotNull(result);
        assertEquals(2, result);
    }

    @Test
    public void whenCountingElementsInStream_thenOK() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        long count = numbers.stream()
          .filter(n -> n % 2 == 0)
          .count();

        assertEquals(5, count);
    }

    @Test
    public void whenCheckingAllMatch_thenOK() {
        List<Integer> numbers = Arrays.asList(2, 4, 6, 8, 10);

        boolean allEven = numbers.stream()
          .allMatch(n -> n % 2 == 0);

        assertTrue(allEven);
    }

}
