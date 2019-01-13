package com.baeldung.list.check;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.hamcrest.Matchers;
import org.junit.Test;

import com.google.common.collect.Iterables;

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
   
        assertTrue(distinctList.stream()
            .allMatch(distinctList.get(0) :: equals));

        assertTrue(distinctList.parallelStream()
            .allMatch(distinctList.get(0) :: equals));

        // 2
        assertTrue(Collections.frequency(distinctList, distinctList.get(0)) == distinctList.size());

        // 3
        assertTrue(new HashSet<>(distinctList).size() == 1);
        
        // 4
        assertTrue(IterableUtils.countMatches(distinctList, e -> e.equals(distinctList.get(0))) == distinctList.size());
        
        assertTrue(IterableUtils.frequency(distinctList, distinctList.get(0)) == distinctList.size()) ;
        
        assertTrue(IterableUtils.matchesAll(distinctList,  e -> e.equals(distinctList.get(0))));
        
        // 5
        assertTrue(Iterables.all(distinctList,  e -> e.equals(distinctList.get(0))));
        
        assertTrue(Iterables.frequency(distinctList, distinctList.get(0)) == distinctList.size());
        
        // 6
        assertThat(distinctList, Matchers.everyItem(Matchers.comparesEqualTo(distinctList.get(0))));
        
    }

    @Test
    public void whenNonDistinctList_thenAssertFalse() {

        // 1
        assertFalse(nonDistinctList.stream()
            .allMatch(e -> e.equals(nonDistinctList.get(0))));

        assertFalse(nonDistinctList.parallelStream()
            .allMatch(e -> e.equals(nonDistinctList.get(0))));
        
        assertFalse(nonDistinctList.stream()
            .allMatch(nonDistinctList.get(0) :: equals));

        assertFalse(nonDistinctList.parallelStream()
            .allMatch(nonDistinctList.get(0) :: equals));

        // 2
        assertFalse(Collections.frequency(nonDistinctList, nonDistinctList.get(0)) == nonDistinctList.size());

        // 3
        assertFalse(new HashSet<>(nonDistinctList).size() == 1);
        
        // 4
        assertFalse(IterableUtils.countMatches(nonDistinctList, e -> e.equals(nonDistinctList.get(0))) == nonDistinctList.size());
        
        assertFalse(IterableUtils.frequency(nonDistinctList, nonDistinctList.get(0)) == nonDistinctList.size());
        
        assertFalse(IterableUtils.matchesAll(nonDistinctList, e -> e.equals(nonDistinctList.get(0))));
        
        // 5
        assertFalse(Iterables.all(nonDistinctList,  e -> e.equals(nonDistinctList.get(0))));
        
        assertFalse(Iterables.frequency(nonDistinctList, nonDistinctList.get(0)) == nonDistinctList.size());
        
        // 6
        assertThat(nonDistinctList, Matchers.not(Matchers.everyItem(Matchers.comparesEqualTo(nonDistinctList.get(0)))));
    }
}