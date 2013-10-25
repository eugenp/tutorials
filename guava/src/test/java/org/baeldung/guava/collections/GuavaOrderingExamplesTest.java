package org.baeldung.guava.collections;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

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
        assertThat(nums.get(nums.size() - 1), nullValue());
    }

    @Test
    public final void givenCollectionWithNulls_whenSortingWithNullsFirst_thenNullsAreFirst() {
        final List<Integer> nums = Arrays.asList(3, 5, 4, null, 1, 2);
        Collections.sort(nums, Ordering.natural().nullsFirst());
        assertThat(nums.get(0), nullValue());
    }

    @Test
    public final void whenCollectionIsSortedNullsLastReversed_thenNullAreFirst() {
        final List<Integer> nums = Arrays.asList(3, 5, 4, null, 1, 2);
        Collections.sort(nums, Ordering.natural().nullsLast().reverse());
        assertThat(nums.get(0), nullValue());
    }
}
