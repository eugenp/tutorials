package com.baeldung.set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.SetUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SetOperationsUnitTest {

    private Set<Integer> setA = setOf(1, 2, 3, 4);
    private Set<Integer> setB = setOf(2, 4, 6, 8);

    private static Set<Integer> setOf(Integer... values) {
        return new HashSet<Integer>(Arrays.asList(values));
    }

    @Test
    public void givenTwoSets_WhenWeRetainAll_ThenWeIntersectThem() {
        Set<Integer> intersectSet = new HashSet<>(setA);
        intersectSet.retainAll(setB);
        assertEquals(setOf(2, 4), intersectSet);
    }

    @Test
    public void givenTwoSets_WhenWeAddAll_ThenWeUnionThem() {
        Set<Integer> unionSet = new HashSet<>(setA);
        unionSet.addAll(setB);
        assertEquals(setOf(1, 2, 3, 4, 6, 8), unionSet);
    }

    @Test
    public void givenTwoSets_WhenRemoveAll_ThenWeGetTheDifference() {
        Set<Integer> differenceSet = new HashSet<>(setA);
        differenceSet.removeAll(setB);
        assertEquals(setOf(1, 3), differenceSet);
    }

    @Test
    public void givenTwoStreams_WhenWeFilterThem_ThenWeCanGetTheIntersect() {
        Set<Integer> intersectSet = setA.stream()
          .filter(setB::contains)
          .collect(Collectors.toSet());
        assertEquals(setOf(2, 4), intersectSet);
    }

    @Test
    public void givenTwoStreams_WhenWeConcatThem_ThenWeGetTheUnion() {
        Set<Integer> unionSet = Stream.concat(setA.stream(), setB.stream())
          .collect(Collectors.toSet());
        assertEquals(setOf(1, 2, 3, 4, 6, 8), unionSet);
    }

    @Test
    public void givenTwoStreams_WhenWeFilterThem_ThenWeCanGetTheDifference() {
        Set<Integer> differenceSet = setA.stream()
          .filter(val -> !setB.contains(val))
          .collect(Collectors.toSet());
        assertEquals(setOf(1, 3), differenceSet);
    }

    @Test
    public void givenTwoSets_WhenWeUseApacheCommonsIntersect_ThenWeGetTheIntersect() {
        Set<Integer> intersectSet = SetUtils.intersection(setA, setB);
        assertEquals(setOf(2, 4), intersectSet);
    }

    @Test
    public void givenTwoSets_WhenWeUseApacheCommonsUnion_ThenWeGetTheUnion() {
        Set<Integer> unionSet = SetUtils.union(setA, setB);
        assertEquals(setOf(1, 2, 3, 4, 6, 8), unionSet);
    }


    @Test
    public void givenTwoSets_WhenWeUseGuavaIntersect_ThenWeGetTheIntersect() {
        Set<Integer> intersectSet = Sets.intersection(setA, setB);
        assertEquals(setOf(2, 4), intersectSet);
    }

    @Test
    public void givenTwoSets_WhenWeUseGuavaUnion_ThenWeGetTheUnion() {
        Set<Integer> unionSet = Sets.union(setA, setB);
        assertEquals(setOf(1, 2, 3, 4, 6, 8), unionSet);
    }

    @Test
    public void givenASet_whenUsingStreams_thenCreateSubset() {
        // We use a LinkedHashSet to ensure a predictable order for the subset
        Set<Integer> orderedSet = new LinkedHashSet<>(setA);
        Set<Integer> subset = orderedSet.stream()
          .limit(2)
          .collect(Collectors.toSet());

        assertEquals(setOf(1, 2), subset);
    }

    @Test
    public void givenASet_whenUsingGuava_thenCreateSubset() {
        // Succinctly create a subset of the first 2 elements
        Set<Integer> orderedSet = new LinkedHashSet<>(setA);
        Set<Integer> subset = ImmutableSet.copyOf(Iterables.limit(orderedSet, 2));

        assertEquals(setOf(1, 2), subset);
    }

    @Test
    public void givenATreeSet_whenUsingSubSet_thenCreateView() {
        NavigableSet<Integer> sortedSet = new TreeSet<>(setA);

        // Returns elements from 1 (inclusive) to 3 (exclusive)
        Set<Integer> subset = sortedSet.subSet(1, 3);

        assertEquals(setOf(1, 2), subset);

        // Demonstrating the "view" nature: changes to original reflect in subset
        sortedSet.remove(1);
        assertFalse(subset.contains(1));
        assertEquals(1, subset.size());
    }
}