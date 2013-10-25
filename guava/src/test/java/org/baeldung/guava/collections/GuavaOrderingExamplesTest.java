package org.baeldung.guava.collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

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

    @Test
    public final void whenSortingCollectionsOfStringsByLenght_thenCorrectlySorted() {
        final List<String> toSort = Arrays.asList("aa", "b", null, "ccc");
        final Ordering<String> byLength = new Ordering<String>() {
            @Override
            public int compare(final String s1, final String s2) {
                return Ints.compare(s1.length(), s2.length());
            }
        };

        Collections.sort(toSort, byLength.nullsFirst());
        assertThat(toSort.get(0), nullValue());
        assertThat(toSort.get(1), equalTo("b"));
    }

}
