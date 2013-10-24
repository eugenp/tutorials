package org.baeldung.guava.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Ordering;

public class GuavaOrderingExamplesTest {

    // tests

    @Test
    public final void givenCollectionWithNulls_whenSortingWithNullsLast_thenNullsAreLast() {
        final List<Integer> nums = Arrays.asList(3, 5, 4, null, 1, 2);
        Collections.sort(nums, Ordering.natural().nullsLast());
        System.out.println(nums);
    }

    @Test
    public final void givenCollectionWithNulls_whenSortingWithNullsFirst_thenNullsAreFirst() {
        final List<Integer> nums = Arrays.asList(3, 5, 4, null, 1, 2);
        Collections.sort(nums, Ordering.natural().nullsFirst());
        System.out.println(nums);
    }

}
