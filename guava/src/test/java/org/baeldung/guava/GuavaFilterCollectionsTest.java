package org.baeldung.guava;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;

public class GuavaFilterCollectionsTest {

    @Test
    public void whenFilterCollection_thenFiltered() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final Collection<String> result = Collections2.filter(names, Predicates.containsPattern("a"));

        assertEquals(2, result.size());
        assertEquals("[Jane, Adam]", result.toString());
    }

    @Test
    public void whenFilterCollectionWithCustomPredicate_thenFiltered() {
        final Predicate<String> predicate = new Predicate<String>() {
            @Override
            public final boolean apply(final String input) {
                return input.startsWith("A") || input.startsWith("J");
            }
        };

        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final Collection<String> result = Collections2.filter(names, predicate);

        assertEquals(3, result.size());
        assertEquals("[John, Jane, Adam]", result.toString());
    }

    @Test
    public void whenFilterUsingMultiplePredicates_thenFiltered() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final Collection<String> result = Collections2.filter(names, Predicates.or(Predicates.containsPattern("J"), Predicates.not(Predicates.containsPattern("a"))));

        assertEquals(3, result.size());
        assertEquals("[John, Jane, Tom]", result.toString());
    }

    @Test
    public void whenTransformCollection_thenTransformed() {
        final Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public final Integer apply(final String input) {
                return input.length();
            }
        };

        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final Collection<Integer> result = Collections2.transform(names, function);

        assertEquals(4, result.size());
        assertEquals("[4, 4, 4, 3]", result.toString());
    }

    @Test
    public void whenTransformUsingComposedFunction_thenTransformed() {
        final Function<String, Integer> f1 = new Function<String, Integer>() {
            @Override
            public final Integer apply(final String input) {
                return input.length();
            }
        };

        final Function<Integer, Boolean> f2 = new Function<Integer, Boolean>() {
            @Override
            public final Boolean apply(final Integer input) {
                return input % 2 == 0;
            }
        };

        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final Collection<Boolean> result = Collections2.transform(names, Functions.compose(f2, f1));

        assertEquals(4, result.size());
        assertEquals("[true, true, true, false]", result.toString());
    }

    @Test
    public void whenFilterAndTransformCollection_thenCorrect() {
        final Predicate<String> predicate = new Predicate<String>() {
            @Override
            public final boolean apply(final String input) {
                return input.startsWith("A") || input.startsWith("T");
            }
        };

        final Function<String, Integer> func = new Function<String, Integer>() {
            @Override
            public final Integer apply(final String input) {
                return input.length();
            }
        };

        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final Collection<Integer> result = FluentIterable.from(names).filter(predicate).transform(func).toList();

        assertEquals(2, result.size());
        assertEquals("[4, 3]", result.toString());
    }

    @Test
    public void whenSortCollection_thenSorted() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        Collections.sort(names, Ordering.natural());
        assertEquals("[Adam, Jane, John, Tom]", names.toString());
    }

    @Test
    public void whenSortCollectionUsingCustomOrdering_thenSorted() {
        final Ordering<String> ordering = Ordering.natural().onResultOf(new Function<String, Integer>() {
            @Override
            public final Integer apply(final String input) {
                return input.length();
            }
        });

        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        Collections.sort(names, ordering);
        assertEquals("[Tom, John, Jane, Adam]", names.toString());
    }

    @Test
    public void whenGroupCollection_thenGrouped() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final ListMultimap<Integer, String> groups = Multimaps.index(names, new Function<String, Integer>() {
            @Override
            public final Integer apply(final String input) {
                return input.length();
            }
        });

        assertEquals(2, groups.keySet().size());
        assertEquals(3, groups.get(4).size());
        assertEquals("[Tom]", groups.get(3).toString());
    }

}

