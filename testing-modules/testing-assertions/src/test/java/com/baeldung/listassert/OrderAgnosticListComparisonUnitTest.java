package com.baeldung.listassert;

import org.apache.commons.collections4.CollectionUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderAgnosticListComparisonUnitTest {

    private final List<Integer> first = Arrays.asList(1, 3, 4, 6, 8);
    private final List<Integer> second = Arrays.asList(8, 1, 6, 3, 4);
    private final List<Integer> third = Arrays.asList(1, 3, 3, 6, 6);

    @Test
    public void whenTestingForOrderAgnosticEquality_ShouldBeTrue() {
        assertTrue(first.size() == second.size() && first.containsAll(second) && second.containsAll(first));
    }

    @Test
    public void whenTestingForOrderAgnosticEquality_ShouldBeFalse() {
        assertFalse(first.size() == third.size() && first.containsAll(third) && third.containsAll(first));
    }

    @Test
    public void whenTestingForOrderAgnosticEquality_ShouldBeEqual() {
        assertThat(first, Matchers.containsInAnyOrder(second.toArray()));
    }

    @Test
    public void whenTestingForOrderAgnosticEquality_ShouldBeTrueIfEqualOtherwiseFalse() {
        assertTrue(CollectionUtils.isEqualCollection(first, second));
        assertFalse(CollectionUtils.isEqualCollection(first, third));
    }
}
