package com.baeldung.doubleminusgreaterthan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

public class DoubleMinusAndGreaterThanUnitTest {

    @Test
    void whenUsingDoubleMinusAndGreaterThan_thenGetTheExpectedResult() {
        List<Integer> resultWhile = new ArrayList<>();
        int i = 6;
        while (i-- > 0) {
            resultWhile.add(i);
        }
        assertEquals(Lists.newArrayList(5, 4, 3, 2, 1, 0), resultWhile);

        List<Integer> resultFor = new ArrayList<>();
        for (int j = 6; j-- > 0; ) {
            resultFor.add(j);
        }

        assertEquals(Lists.newArrayList(5, 4, 3, 2, 1, 0), resultFor);
    }

    @Test
    void whenUsingOtherOperatorsSimilarly_thenGetTheExpectedResult() {
        // <++
        List<Integer> result = new ArrayList<>();
        int i = -1;
        while (i++ < 5) {
            result.add(i);
        }
        assertEquals(Lists.newArrayList(0, 1, 2, 3, 4, 5), result);

        // <--
        result.clear();
        int j = 10;
        while (0 < --j) {
            result.add(j);
        }
        assertEquals(Lists.newArrayList(9, 8, 7, 6, 5, 4, 3, 2, 1), result);

        // >++
        result.clear();
        int n = 0;
        while (6 > ++n) {
            result.add(n);
        }
        assertEquals(Lists.newArrayList(1, 2, 3, 4, 5), result);

        // >>>=
        result.clear();
        int x = 32;
        while ((x >>>= 1) > 1) {
            result.add(x);
        }
        assertEquals(Lists.newArrayList(16, 8, 4, 2), result);

    }
}