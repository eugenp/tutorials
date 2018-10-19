package org.baeldung.guava;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class GuavaFilterTransformCollectionsUnitTest {

    @Test
    public void whenFilterWithIterables_thenFiltered() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final Iterable<String> result = Iterables.filter(names, Predicates.containsPattern("a"));

        assertThat(result, containsInAnyOrder("Jane", "Adam"));
    }

    @Test
    public void whenFilterWithCollections2_thenFiltered() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final Collection<String> result = Collections2.filter(names, Predicates.containsPattern("a"));

        assertEquals(2, result.size());
        assertThat(result, containsInAnyOrder("Jane", "Adam"));

        result.add("anna");
        assertEquals(5, names.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenFilteredCollection_whenAddingInvalidElement_thenException() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final Collection<String> result = Collections2.filter(names, Predicates.containsPattern("a"));

        result.add("elvis");
    }

    //

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
        assertThat(result, containsInAnyOrder("John", "Jane", "Adam"));
    }

    //

    @Test
    public void whenRemoveNullFromCollection_thenRemoved() {
        final List<String> names = Lists.newArrayList("John", null, "Jane", null, "Adam", "Tom");
        final Collection<String> result = Collections2.filter(names, Predicates.notNull());

        assertEquals(4, result.size());
        assertThat(result, containsInAnyOrder("John", "Jane", "Adam", "Tom"));
    }

    //

    @Test
    public void whenCheckingIfAllElementsMatchACondition_thenCorrect() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");

        boolean result = Iterables.all(names, Predicates.containsPattern("n|m"));
        assertTrue(result);

        result = Iterables.all(names, Predicates.containsPattern("a"));
        assertFalse(result);
    }

    //

    @Test
    public void whenTransformingWithIterables_thenTransformed() {
        final Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public final Integer apply(final String input) {
                return input.length();
            }
        };

        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final Iterable<Integer> result = Iterables.transform(names, function);

        assertThat(result, contains(4, 4, 4, 3));
    }

    //

    @Test
    public void whenTransformWithCollections2_thenTransformed() {
        final Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public final Integer apply(final String input) {
                return input.length();
            }
        };

        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final Collection<Integer> result = Collections2.transform(names, function);

        assertEquals(4, result.size());
        assertThat(result, contains(4, 4, 4, 3));

        result.remove(3);
        assertEquals(3, names.size());
    }

    //

    @Test
    public void whenCreatingAFunctionFromAPredicate_thenCorrect() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final Collection<Boolean> result = Collections2.transform(names, Functions.forPredicate(Predicates.containsPattern("m")));

        assertEquals(4, result.size());
        assertThat(result, contains(false, false, true, true));
    }

    //

    @Test
    public void whenTransformingUsingComposedFunction_thenTransformed() {
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
        assertThat(result, contains(true, true, true, false));
    }

    //

    @Test
    public void whenFilteringAndTransformingCollection_thenCorrect() {
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
        assertThat(result, containsInAnyOrder(4, 3));
    }

    //

    @Test
    public void whenFilterUsingMultiplePredicates_thenFiltered() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final Collection<String> result = Collections2.filter(names, Predicates.or(Predicates.containsPattern("J"), Predicates.not(Predicates.containsPattern("a"))));

        assertEquals(3, result.size());
        assertThat(result, containsInAnyOrder("John", "Jane", "Tom"));
    }

}
