package com.baeldung.guava;

import com.google.common.base.Throwables;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.Assert.*;

public class GuavaMiscUtilsUnitTest {

    @Test
    public void whenGettingLazyStackTrace_ListShouldBeReturned() throws Exception {
        IllegalArgumentException e = new IllegalArgumentException("Some argument is incorrect");

        List<StackTraceElement> stackTraceElements = Throwables.lazyStackTrace(e);

        assertTrue(stackTraceElements.size() > 0);
    }

    @Test
    public void multisetShouldCountHitsOfMultipleDuplicateObjects() throws Exception {
        List<String> userNames = Arrays.asList("David", "Eugene", "Alex", "Alex", "David", "David", "David");

        Multiset<String> userNamesMultiset = HashMultiset.create(userNames);

        assertEquals(7, userNamesMultiset.size());
        assertEquals(4, userNamesMultiset.count("David"));
        assertEquals(2, userNamesMultiset.count("Alex"));
        assertEquals(1, userNamesMultiset.count("Eugene"));
        assertThat(userNamesMultiset.elementSet(), anyOf(containsInAnyOrder("Alex", "David", "Eugene")));
    }

    @Test
    public void whenAddingNewConnectedRange_RangesShouldBeMerged() throws Exception {
        RangeSet<Integer> rangeSet = TreeRangeSet.create();

        rangeSet.add(Range.closed(1, 10));
        rangeSet.add(Range.closed(5, 15));
        rangeSet.add(Range.closedOpen(10, 17));

        assertTrue(rangeSet.encloses(Range.closedOpen(1, 17)));
        assertTrue(rangeSet.encloses(Range.closed(2, 3)));
        assertTrue(rangeSet.contains(15));
        assertFalse(rangeSet.contains(17));
        assertEquals(1, rangeSet.asDescendingSetOfRanges().size());
    }

    @Test
    public void cartesianProductShouldReturnAllPossibleCombinations() throws Exception {
        List<String> first = Lists.newArrayList("value1", "value2");
        List<String> second = Lists.newArrayList("value3", "value4");

        List<List<String>> cartesianProduct = Lists.cartesianProduct(first, second);

        List<String> pair1 = Lists.newArrayList("value2", "value3");
        List<String> pair2 = Lists.newArrayList("value2", "value4");
        List<String> pair3 = Lists.newArrayList("value1", "value3");
        List<String> pair4 = Lists.newArrayList("value1", "value4");

        assertThat(cartesianProduct, anyOf(containsInAnyOrder(pair1, pair2, pair3, pair4)));
    }

    @Test
    public void multisetShouldRemoveOccurrencesOfSpecifiedObjects() throws Exception {
        Multiset<String> multisetToModify = HashMultiset.create();
        Multiset<String> occurrencesToRemove = HashMultiset.create();

        multisetToModify.add("John");
        multisetToModify.add("Max");
        multisetToModify.add("Alex");

        occurrencesToRemove.add("Alex");
        occurrencesToRemove.add("John");

        Multisets.removeOccurrences(multisetToModify, occurrencesToRemove);

        assertEquals(1, multisetToModify.size());
        assertTrue(multisetToModify.contains("Max"));
        assertFalse(multisetToModify.contains("John"));
        assertFalse(multisetToModify.contains("Alex"));
    }
}