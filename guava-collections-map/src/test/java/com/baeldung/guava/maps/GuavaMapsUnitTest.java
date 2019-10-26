package com.baeldung.guava.maps;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.*;

public class GuavaMapsUnitTest {

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
