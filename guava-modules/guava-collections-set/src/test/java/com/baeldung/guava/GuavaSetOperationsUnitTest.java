package com.baeldung.guava;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GuavaSetOperationsUnitTest {

    @Test
    public void whenCalculatingUnionOfSets_thenCorrect() {
        final Set<Character> first = ImmutableSet.of('a', 'b', 'c');
        final Set<Character> second = ImmutableSet.of('b', 'c', 'd');

        final Set<Character> union = Sets.union(first, second);
        assertThat(union, containsInAnyOrder('a', 'b', 'c', 'd'));
    }

    @Test
    public void whenCalculatingCartesianProductOfSets_thenCorrect() {
        final Set<Character> first = ImmutableSet.of('a', 'b');
        final Set<Character> second = ImmutableSet.of('c', 'd');
        final Set<List<Character>> result = Sets.cartesianProduct(ImmutableList.of(first, second));

        final Function<List<Character>, String> func = new Function<List<Character>, String>() {
            @Override
            public final String apply(final List<Character> input) {
                return Joiner
                  .on(" ").join(input);
            }
        };

        final Iterable<String> joined = Iterables.transform(result, func);
        assertThat(joined, containsInAnyOrder("a c", "a d", "b c", "b d"));
    }

    @Test
    public void whenCalculatingSetIntersection_thenCorrect() {
        final Set<Character> first = ImmutableSet.of('a', 'b', 'c');
        final Set<Character> second = ImmutableSet.of('b', 'c', 'd');

        final Set<Character> intersection = Sets.intersection(first, second);
        assertThat(intersection, containsInAnyOrder('b', 'c'));
    }

    @Test
    public void whenCalculatingSetSymmetricDifference_thenCorrect() {
        final Set<Character> first = ImmutableSet.of('a', 'b', 'c');
        final Set<Character> second = ImmutableSet.of('b', 'c', 'd');

        final Set<Character> intersection = Sets.symmetricDifference(first, second);
        assertThat(intersection, containsInAnyOrder('a', 'd'));
    }

    @Test
    public void whenCalculatingPowerSet_thenCorrect() {
        final Set<Character> chars = ImmutableSet.of('a', 'b');
        final Set<Set<Character>> result = Sets.powerSet(chars);

        final Set<Character> empty = ImmutableSet.<Character> builder().build();
        final Set<Character> a = ImmutableSet.of('a');
        final Set<Character> b = ImmutableSet.of('b');
        final Set<Character> aB = ImmutableSet.of('a', 'b');

        assertThat(result, contains(empty, a, b, aB));
    }

    @Test
    public void whenCreatingRangeOfIntegersSet_thenCreated() {
        final int start = 10;
        final int end = 30;
        final ContiguousSet<Integer> set = ContiguousSet.create(Range.closed(start, end), DiscreteDomain.integers());

        assertEquals(21, set.size());
        assertEquals(10, set.first().intValue());
        assertEquals(30, set.last().intValue());
    }

    @Test
    public void whenUsingRangeSet_thenCorrect() {
        final RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10));
        rangeSet.add(Range.closed(12, 15));

        assertEquals(2, rangeSet.asRanges().size());

        rangeSet.add(Range.closed(10, 12));
        assertTrue(rangeSet.encloses(Range.closed(1, 15)));
        assertEquals(1, rangeSet.asRanges().size());
    }

    @Test
    public void whenInsertDuplicatesInMultiSet_thenInserted() {
        final Multiset<String> names = HashMultiset.create();
        names.add("John");
        names.add("Adam", 3);
        names.add("John");

        assertEquals(2, names.count("John"));
        names.remove("John");
        assertEquals(1, names.count("John"));

        assertEquals(3, names.count("Adam"));
        names.remove("Adam", 2);
        assertEquals(1, names.count("Adam"));
    }

    @Test
    public void whenGetTopOcurringElementsWithMultiSet_thenCorrect() {
        final Multiset<String> names = HashMultiset.create();
        names.add("John");
        names.add("Adam", 5);
        names.add("Jane");
        names.add("Tom", 2);

        final Set<String> sorted = Multisets.copyHighestCountFirst(names).elementSet();
        final List<String> topTwo = Lists.newArrayList(sorted).subList(0, 2);
        assertEquals(2, topTwo.size());
        assertEquals("Adam", topTwo.get(0));
        assertEquals("Tom", topTwo.get(1));
    }
}
