package org.baeldung.guava.collections;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

public class GuavaOrderingExamplesTest {

    private final class OrderingByLenght extends Ordering<String> {
        @Override
        public final int compare(final String s1, final String s2) {
            return Ints.compare(s1.length(), s2.length());
        }
    }

    // tests

    @Test
    public final void givenCollectionWithNulls_whenSortingWithNullsLast_thenNullsAreLast() {
        final List<Integer> toSort = Arrays.asList(3, 5, 4, null, 1, 2);
        Collections.sort(toSort, Ordering.natural().nullsLast());
        assertThat(toSort.get(toSort.size() - 1), nullValue());
    }

    @Test
    public final void givenCollectionWithNulls_whenSortingWithNullsFirst_thenNullsAreFirst() {
        final List<Integer> toSort = Arrays.asList(3, 5, 4, null, 1, 2);
        Collections.sort(toSort, Ordering.natural().nullsFirst());
        assertThat(toSort.get(0), nullValue());
    }

    @Test
    public final void whenCollectionIsSortedNullsLastReversed_thenNullAreFirst() {
        final List<Integer> toSort = Arrays.asList(3, 5, 4, null, 1, 2);
        Collections.sort(toSort, Ordering.natural().nullsLast().reverse());
        assertThat(toSort.get(0), nullValue());
    }

    @Test
    public final void givenCollectionIsSorted_whenUsingOrderingApiToCheckOrder_thenCheckCanBePerformed() {
        final List<String> toSort = Arrays.asList("zz", "aa", "b", "ccc");
        final Ordering<String> byLength = new OrderingByLenght();
        Collections.sort(toSort, byLength);

        final Ordering<String> expectedOrder = Ordering.explicit(Lists.newArrayList("b", "zz", "aa", "ccc"));
        assertTrue(expectedOrder.isOrdered(toSort));
    }

    @Test
    public final void whenSortingCollectionsOfStringsByLenght_thenCorrectlySorted() {
        final List<String> toSort = Arrays.asList("zz", "aa", "b", "ccc");
        final Ordering<String> byLength = new OrderingByLenght();

        Collections.sort(toSort, byLength);

        final Ordering<String> expectedOrder = Ordering.explicit(Lists.newArrayList("b", "zz", "aa", "ccc"));
        assertTrue(expectedOrder.isOrdered(toSort));
    }

    @Test
    public final void whenSortingCollectionsOfStringsByLenghtWithSecondaryNaturalOrdering_thenCorrectlySorted() {
        final List<String> toSort = Arrays.asList("zz", "aa", "b", "ccc");
        final Ordering<String> byLength = new OrderingByLenght();

        Collections.sort(toSort, byLength.compound(Ordering.natural()));

        final Ordering<String> expectedOrder = Ordering.explicit(Lists.newArrayList("b", "aa", "zz", "ccc"));
        assertTrue(expectedOrder.isOrdered(toSort));
    }

}
