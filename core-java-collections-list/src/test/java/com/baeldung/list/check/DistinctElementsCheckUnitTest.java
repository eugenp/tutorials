package com.baeldung.list.check;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class DistinctElementsCheckUnitTest {

    private List<String> nonDistinctList = Arrays.asList("AB", "XY", "AB", "XY");

    private List<String> distinctList = Arrays.asList("AB", "AB", "AB", "AB");

    @Test
    public void whenDistinctList_thenAssertTrue() {

        // 1
        assertTrue(distinctList.stream()
            .allMatch(e -> e.equals(distinctList.get(0))));

        assertTrue(distinctList.parallelStream()
            .allMatch(e -> e.equals(distinctList.get(0))));

        // 2
        assertTrue(distinctList.stream()
            .allMatch(distinctList.get(0)::equals));

        assertTrue(distinctList.parallelStream()
            .allMatch(distinctList.get(0)::equals));

        // 3
        assertTrue(Collections.frequency(distinctList, distinctList.get(0)) == distinctList.size());

        // 4
        assertTrue(distinctList.size() > 0 && distinctList.lastIndexOf(distinctList.get(0)) == distinctList.size() - 1);
    }
    

    @Test
    public void whenNonDistinctList_thenAssertFalse() {

        // 1
        assertFalse(nonDistinctList.stream()
            .allMatch(e -> e.equals(nonDistinctList.get(0))));

        assertFalse(nonDistinctList.parallelStream()
            .allMatch(e -> e.equals(nonDistinctList.get(0))));

        // 2
        assertFalse(nonDistinctList.stream()
            .allMatch(nonDistinctList.get(0)::equals));

        assertFalse(nonDistinctList.parallelStream()
            .allMatch(nonDistinctList.get(0)::equals));

        // 3
        assertFalse(Collections.frequency(nonDistinctList, nonDistinctList.get(0)) == nonDistinctList.size());

        // 4
        assertFalse(nonDistinctList.size() > 0 && nonDistinctList.lastIndexOf(nonDistinctList.get(0)) == nonDistinctList.size() - 1);
    }
}