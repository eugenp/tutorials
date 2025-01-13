package com.baeldung.set;

import static org.junit.Assert.*;

import org.apache.commons.collections4.SetUtils;
import org.junit.Test;

import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetOperationsUnitTest {

    private Set<Integer> setA = setOf(1,2,3,4);
    private Set<Integer> setB = setOf(2,4,6,8);
    
    private static Set<Integer> setOf(Integer... values) {
        return new HashSet<Integer>(Arrays.asList(values));
    }
    
    @Test
    public void givenTwoSets_WhenWeRetainAll_ThenWeIntersectThem() {
        Set<Integer> intersectSet = new HashSet<>(setA);
        intersectSet.retainAll(setB);
        assertEquals(setOf(2,4), intersectSet);
    }
    
    @Test
    public void givenTwoSets_WhenWeAddAll_ThenWeUnionThem() {
        Set<Integer> unionSet = new HashSet<>(setA);
        unionSet.addAll(setB);
        assertEquals(setOf(1,2,3,4,6,8), unionSet);
    }
    
    @Test
    public void givenTwoSets_WhenRemoveAll_ThenWeGetTheDifference() {
        Set<Integer> differenceSet = new HashSet<>(setA);
        differenceSet.removeAll(setB);
        assertEquals(setOf(1,3), differenceSet);
    }
    
    @Test
    public void givenTwoStreams_WhenWeFilterThem_ThenWeCanGetTheIntersect() {
        Set<Integer> intersectSet = setA.stream()
            .filter(setB::contains)
            .collect(Collectors.toSet());
        assertEquals(setOf(2,4), intersectSet);
    }
    
    @Test
    public void givenTwoStreams_WhenWeConcatThem_ThenWeGetTheUnion() {
        Set<Integer> unionSet = Stream.concat(setA.stream(), setB.stream())
            .collect(Collectors.toSet());
        assertEquals(setOf(1,2,3,4,6,8), unionSet);
    }
    
    @Test
    public void givenTwoStreams_WhenWeFilterThem_ThenWeCanGetTheDifference() {
        Set<Integer> differenceSet = setA.stream()
            .filter(val -> !setB.contains(val))
            .collect(Collectors.toSet());
        assertEquals(setOf(1,3), differenceSet);
    }
    
    @Test
    public void givenTwoSets_WhenWeUseApacheCommonsIntersect_ThenWeGetTheIntersect() {
        Set<Integer> intersectSet = SetUtils.intersection(setA, setB);
        assertEquals(setOf(2,4), intersectSet);
    }
    
    @Test
    public void givenTwoSets_WhenWeUseApacheCommonsUnion_ThenWeGetTheUnion() {
        Set<Integer> unionSet = SetUtils.union(setA, setB);
        assertEquals(setOf(1,2,3,4,6,8), unionSet);
    }
    
    
    @Test
    public void givenTwoSets_WhenWeUseGuavaIntersect_ThenWeGetTheIntersect() {
        Set<Integer> intersectSet = Sets.intersection(setA, setB);
        assertEquals(setOf(2,4), intersectSet);
    }
    
    @Test
    public void givenTwoSets_WhenWeUseGuavaUnion_ThenWeGetTheUnion() {
        Set<Integer> unionSet = Sets.union(setA, setB);
        assertEquals(setOf(1,2,3,4,6,8), unionSet);
    }
}
