package org.baeldung.guava;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.collect.TreeRangeSet;

public class GuavaCollectionTypesUnitTest {

    @Test
    public void whenCreateList_thenCreated() {
        final List<String> names = Lists.newArrayList("John", "Adam", "Jane");

        names.add("Tom");
        assertEquals(4, names.size());

        names.remove("Adam");
        assertThat(names, contains("John", "Jane", "Tom"));
    }

    @Test
    public void whenReverseList_thenReversed() {
        final List<String> names = Lists.newArrayList("John", "Adam", "Jane");

        final List<String> reversed = Lists.reverse(names);
        assertThat(reversed, contains("Jane", "Adam", "John"));
    }

    @Test
    public void whenCreateCharacterListFromString_thenCreated() {
        final List<Character> chars = Lists.charactersOf("John");

        assertEquals(4, chars.size());
        assertThat(chars, contains('J', 'o', 'h', 'n'));
    }

    @Test
    public void whenPartitionList_thenPartitioned() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom", "Viki", "Tyler");
        final List<List<String>> result = Lists.partition(names, 2);

        assertEquals(3, result.size());
        assertThat(result.get(0), contains("John", "Jane"));
        assertThat(result.get(1), contains("Adam", "Tom"));
        assertThat(result.get(2), contains("Viki", "Tyler"));
    }

    @Test
    public void whenRemoveDuplicatesFromList_thenRemoved() {
        final List<Character> chars = Lists.newArrayList('h', 'e', 'l', 'l', 'o');
        assertEquals(5, chars.size());

        final List<Character> result = ImmutableSet.copyOf(chars).asList();
        assertThat(result, contains('h', 'e', 'l', 'o'));
    }

    @Test
    public void whenRemoveNullFromList_thenRemoved() {
        final List<String> names = Lists.newArrayList("John", null, "Adam", null, "Jane");
        Iterables.removeIf(names, Predicates.isNull());

        assertEquals(3, names.size());
        assertThat(names, contains("John", "Adam", "Jane"));
    }

    @Test
    public void whenCreateImmutableList_thenCreated() {
        final List<String> names = Lists.newArrayList("John", "Adam", "Jane");

        names.add("Tom");
        assertEquals(4, names.size());

        final ImmutableList<String> immutable = ImmutableList.copyOf(names);
        assertThat(immutable, contains("John", "Adam", "Jane", "Tom"));
    }

    // sets

    @Test
    public void whenCalculateUnionOfSets_thenCorrect() {
        final Set<Character> first = ImmutableSet.of('a', 'b', 'c');
        final Set<Character> second = ImmutableSet.of('b', 'c', 'd');

        final Set<Character> union = Sets.union(first, second);
        assertThat(union, containsInAnyOrder('a', 'b', 'c', 'd'));
    }

    @Test
    public void whenCalculateSetsProduct_thenCorrect() {
        final Set<Character> first = ImmutableSet.of('a', 'b');
        final Set<Character> second = ImmutableSet.of('c', 'd');
        final Set<List<Character>> result = Sets.cartesianProduct(ImmutableList.of(first, second));

        final Function<List<Character>, String> func = new Function<List<Character>, String>() {
            @Override
            public final String apply(final List<Character> input) {
                return Joiner.on(" ").join(input);
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
    public void whenCreateRangeOfIntegersSet_thenCreated() {
        final int start = 10;
        final int end = 30;
        final ContiguousSet<Integer> set = ContiguousSet.create(Range.closed(start, end), DiscreteDomain.integers());

        assertEquals(21, set.size());
        assertEquals(10, set.first().intValue());
        assertEquals(30, set.last().intValue());
    }

    @Test
    public void whenCreateRangeSet_thenCreated() {
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
    public void whenGetTopUsingMultiSet_thenCorrect() {
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

    @Test
    public void whenCreateImmutableMap_thenCreated() {
        final Map<String, Integer> salary = ImmutableMap.<String, Integer> builder().put("John", 1000).put("Jane", 1500).put("Adam", 2000).put("Tom", 2000).build();

        assertEquals(1000, salary.get("John").intValue());
        assertEquals(2000, salary.get("Tom").intValue());
    }

    @Test
    public void whenUseSortedMap_thenKeysAreSorted() {
        final ImmutableSortedMap<String, Integer> salary = new ImmutableSortedMap.Builder<String, Integer>(Ordering.natural()).put("John", 1000).put("Jane", 1500).put("Adam", 2000).put("Tom", 2000).build();

        assertEquals("Adam", salary.firstKey());
        assertEquals(2000, salary.lastEntry().getValue().intValue());
    }

    @Test
    public void whenCreateBiMap_thenCreated() {
        final BiMap<String, Integer> words = HashBiMap.create();
        words.put("First", 1);
        words.put("Second", 2);
        words.put("Third", 3);

        assertEquals(2, words.get("Second").intValue());
        assertEquals("Third", words.inverse().get(3));
    }

    @Test
    public void whenCreateMultimap_thenCreated() {
        final Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("fruit", "apple");
        multimap.put("fruit", "banana");
        multimap.put("pet", "cat");
        multimap.put("pet", "dog");

        assertThat(multimap.get("fruit"), containsInAnyOrder("apple", "banana"));
        assertThat(multimap.get("pet"), containsInAnyOrder("cat", "dog"));
    }

    @Test
    public void whenGroupListUsingMultimap_thenGrouped() {
        final List<String> names = Lists.newArrayList("John", "Adam", "Tom");
        final Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public final Integer apply(final String input) {
                return input.length();
            }
        };
        final Multimap<Integer, String> groups = Multimaps.index(names, function);

        assertThat(groups.get(3), containsInAnyOrder("Tom"));
        assertThat(groups.get(4), containsInAnyOrder("John", "Adam"));
    }

    @Test
    public void whenCreateTable_thenCreated() {
        final Table<String, String, Integer> distance = HashBasedTable.create();
        distance.put("London", "Paris", 340);
        distance.put("New York", "Los Angeles", 3940);
        distance.put("London", "New York", 5576);

        assertEquals(3940, distance.get("New York", "Los Angeles").intValue());
        assertThat(distance.columnKeySet(), containsInAnyOrder("Paris", "New York", "Los Angeles"));
        assertThat(distance.rowKeySet(), containsInAnyOrder("London", "New York"));
    }

    @Test
    public void whenTransposeTable_thenCorrect() {
        final Table<String, String, Integer> distance = HashBasedTable.create();
        distance.put("London", "Paris", 340);
        distance.put("New York", "Los Angeles", 3940);
        distance.put("London", "New York", 5576);

        final Table<String, String, Integer> transposed = Tables.transpose(distance);
        assertThat(transposed.rowKeySet(), containsInAnyOrder("Paris", "New York", "Los Angeles"));
        assertThat(transposed.columnKeySet(), containsInAnyOrder("London", "New York"));
    }

    @Test
    public void whenCreateClassToInstanceMap_thenCreated() {
        final ClassToInstanceMap<Number> numbers = MutableClassToInstanceMap.create();
        numbers.putInstance(Integer.class, 1);
        numbers.putInstance(Double.class, 1.5);

        assertEquals(1, numbers.get(Integer.class));
        assertEquals(1.5, numbers.get(Double.class));
    }

}
