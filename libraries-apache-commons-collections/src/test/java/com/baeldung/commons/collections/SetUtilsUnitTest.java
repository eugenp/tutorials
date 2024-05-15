package com.baeldung.commons.collections;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.set.TransformedSet;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SetUtilsUnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void givenSetAndPredicate_whenPredicatedSet_thenValidateSet_and_throw_IllegalArgumentException() {
        Set<String> sourceSet = new HashSet<>();
        sourceSet.addAll(Arrays.asList("London", "Lagos", "Err Source1"));
        Set<String> validatingSet = SetUtils.predicatedSet(sourceSet, (s) -> s.startsWith("L"));
        validatingSet.add("Err Source2");
    }

    @Test
    public void givenTwoSets_whenDifference_thenSetView() {
        Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 5));
        Set<Integer> b = new HashSet<>(Arrays.asList(1, 2));
        SetUtils.SetView<Integer> result = SetUtils.difference(a, b);
        assertTrue(result.size() == 1 && result.contains(5));
    }

    @Test
    public void givenTwoSets_whenUnion_thenUnionResult() {
        Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 5));
        Set<Integer> b = new HashSet<>(Arrays.asList(1, 2));
        Set<Integer> expected = new HashSet<>(Arrays.asList(1, 2, 5));
        SetUtils.SetView<Integer> union = SetUtils.union(a, b);
        assertTrue(SetUtils.isEqualSet(expected, union));
    }

    @Test
    public void givenTwoSets_whenIntersection_thenIntersectionResult() {
        Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 5));
        Set<Integer> b = new HashSet<>(Arrays.asList(1, 2));
        Set<Integer> expected = new HashSet<>(Arrays.asList(1, 2));
        SetUtils.SetView<Integer> intersect = SetUtils.intersection(a, b);
        assertTrue(SetUtils.isEqualSet(expected, intersect));
    }

    @Test
    public void givenSet_whenTransformedSet_thenTransformedResult() {
        Set<Integer> a = SetUtils.transformedSet(new HashSet<>(), (e) -> e * 2);
        a.add(2);
        assertEquals(a.toArray()[0], 4);

        Set<Integer> source = new HashSet<>(Arrays.asList(1));
        Set<Integer> newSet = TransformedSet.transformedSet(source, (e) -> e * 2);
        assertEquals(newSet.toArray()[0], 2);
        assertEquals(source.toArray()[0], 2);
    }

    @Test
    public void givenTwoSet_whenDisjunction_thenDisjunctionSet() {
        Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 5));
        Set<Integer> b = new HashSet<>(Arrays.asList(1, 2, 3));
        SetUtils.SetView<Integer> result = SetUtils.disjunction(a, b);
        assertTrue(result.toSet().contains(5) && result.toSet().contains(3));
    }

    @Test
    public void givenSet_when_OrderedSet_thenMaintainElementOrder() {
        Set<Integer> set = new HashSet<>(Arrays.asList(10, 1, 5));
        System.out.println("unordered set: " + set);

        Set<Integer> orderedSet = SetUtils.orderedSet(new HashSet<>());
        orderedSet.addAll(Arrays.asList(10, 1, 5));
        System.out.println("ordered set = " + orderedSet);
    }
}