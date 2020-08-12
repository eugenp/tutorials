package com.baeldung.listassert;

import org.hamcrest.Matchers;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;


import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderAgnosticListComparison {

    private final List<Integer> first = Arrays.asList(1, 3, 4, 6, 8);
    private final List<Integer> second = Arrays.asList(8, 1, 6, 3, 4);
    private final List<Integer> third = Arrays.asList(1, 3, 3, 6, 6);

    //In this test using simple JUnit assertion
    @Test
    public void whenTestingForOrderAgnosticEqualityShouldBeTrue() {
        assertTrue(first.size() == second.size() &&
                first.containsAll(second) && second.containsAll(first));
    }

    @Test
    public void whenTestingForOrderAgnosticEqualityShouldBeFalse() {
        assertFalse(first.size() == third.size() &&
                first.containsAll(third) && third.containsAll(first));
    }

    //In this test using Hamcrest lib apis for assertion
    @Test
    public void whenTestingForOrderAgnosticEqualityShouldBeEqual() {
        assertThat(first, Matchers.containsInAnyOrder(second.toArray()));
    }

    //In this test asserting lists using Apache Commons apis
    @Test
    public void whenTestingForOrderAgnosticEqualityShouldBeTrueIfEqualOtherwiseFalse() {
        assertTrue(CollectionUtils.isEqualCollection(first, second));
        assertFalse(CollectionUtils.isEqualCollection(first, third));
    }
}
