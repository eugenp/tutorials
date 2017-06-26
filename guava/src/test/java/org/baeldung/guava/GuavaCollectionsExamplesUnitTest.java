package org.baeldung.guava;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@SuppressWarnings("unused")
public class GuavaCollectionsExamplesUnitTest {

    // tests

    @Test
    public final void whenDowncastingGenerifiedCollectionToNewGenerifiedCollection_thenCastIsOK() {
        final class CastFunction<F, T extends F> implements Function<F, T> {
            @SuppressWarnings("unchecked")
            @Override
            public final T apply(final F from) {
                return (T) from;
            }
        }

        final List<Number> originalList = Lists.newArrayList();
        final List<Integer> selectedProducts = Lists.transform(originalList, new CastFunction<Number, Integer>());
        System.out.println(selectedProducts);
    }

    @SuppressWarnings({ "unchecked" })
    @Test
    public final void whenDowncastingGenerifiedCollectionToNewGenerifiedCollection_thenCastIsOK2() {
        final List<Number> originalList = Lists.newArrayList();
        final List<Integer> selectedProducts = (List<Integer>) (List<? extends Number>) originalList;
        System.out.println(selectedProducts);
    }

    @Test
    public final void whenAddingAnIterableToACollection_thenAddedOK() {
        final Iterable<String> iter = Lists.newArrayList();
        final Collection<String> collector = Lists.newArrayList();
        Iterables.addAll(collector, iter);
    }

    //

    @Test
    public final void whenCheckingIfCollectionContainsElementsByCustomMatch1_thenContains() {
        final Iterable<String> theCollection = Lists.newArrayList("a", "bc", "def");
        final boolean contains = Iterables.any(theCollection, new Predicate<String>() {
            @Override
            public final boolean apply(final String input) {
                return input.length() == 1;
            }
        });

        assertTrue(contains);
    }

    @Test
    public final void whenCheckingIfCollectionContainsElementsByCustomMatch2_thenContains() {
        final Set<String> theCollection = Sets.newHashSet("a", "bc", "def");

        final boolean contains = !Sets.filter(theCollection, new Predicate<String>() {
            @Override
            public final boolean apply(final String input) {
                return input.length() == 1;
            }
        }).isEmpty();

        assertTrue(contains);
    }

    @Test
    public final void whenCheckingIfCollectionContainsElementsByCustomMatch3_thenContains() {
        final Iterable<String> theCollection = Sets.newHashSet("a", "bc", "def");

        final boolean contains = Iterables.find(theCollection, new Predicate<String>() {
            @Override
            public final boolean apply(final String input) {
                return input.length() == 1;
            }
        }) != null;

        assertTrue(contains);
    }

    //

    @Test(expected = NoSuchElementException.class)
    public final void givenNoSearchResult_whenFindingElementInIterable_thenException() {
        final Iterable<String> theCollection = Sets.newHashSet("abcd", "efgh", "ijkl");

        final String found = Iterables.find(theCollection, new Predicate<String>() {
            @Override
            public final boolean apply(final String input) {
                return input.length() == 1;
            }
        });

        assertNull(found);
    }

    @Test
    public final void givenNoSearchResult_whenFindingElementInIterableWithSpecifiedReturn_thenNoException() {
        final Iterable<String> theCollection = Sets.newHashSet("abcd", "efgh", "ijkl");

        final Predicate<String> inputOfLengthOne = new Predicate<String>() {
            @Override
            public final boolean apply(final String input) {
                return input.length() == 1;
            }
        };
        final String found = Iterables.find(theCollection, inputOfLengthOne, null);

        assertNull(found);
    }

    // purge of nulls

    @Test
    public final void givenListContainsNulls_whenPurgedOfNulls_thenNoLongerContainsNulls() {
        final List<String> values = Lists.newArrayList("a", null, "b", "c");
        final Iterable<String> withoutNulls = Iterables.filter(values, Predicates.notNull());
        System.out.println(withoutNulls);
    }

    // immutable collections

    @Test
    public final void whenCreatingImuutableCollections_thenNoExceptions() {
        final ImmutableList<String> immutableList = ImmutableList.of("a", "b", "c");
        final ImmutableSet<String> immutableSet = ImmutableSet.of("a", "b", "c");
        final ImmutableMap<String, String> imuttableMap = ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3");
    }

    @Test
    public final void whenTransformingCollectionsToImmutable_thenNoExceptions() {
        final List<String> muttableList = Lists.newArrayList();
        final ImmutableList<String> immutableList = ImmutableList.copyOf(muttableList);

        final Set<String> muttableSet = Sets.newHashSet();
        final ImmutableSet<String> immutableSet = ImmutableSet.copyOf(muttableSet);

        final Map<String, String> muttableMap = Maps.newHashMap();
        final ImmutableMap<String, String> imuttableMap = ImmutableMap.copyOf(muttableMap);
    }

    @Test
    public final void whenTransformingCollectionsToImmutableViaBuilders_thenNoExceptions() {
        final List<String> muttableList = Lists.newArrayList();
        final ImmutableList<String> immutableList = ImmutableList.<String> builder().addAll(muttableList).build();

        final Set<String> muttableSet = Sets.newHashSet();
        final ImmutableSet<String> immutableSet = ImmutableSet.<String> builder().addAll(muttableSet).build();

        final Map<String, String> muttableMap = Maps.newHashMap();
        final ImmutableMap<String, String> imuttableMap = ImmutableMap.<String, String> builder().putAll(muttableMap).build();
    }

    // unmodifiable

    @Test(expected = UnsupportedOperationException.class)
    public final void givenUnmodifiableViewOverIterable_whenTryingToRemove_thenNotAllowed() {
        final List<Integer> numbers = Lists.newArrayList(1, 2, 3);
        final Iterable<Integer> unmodifiableIterable = Iterables.unmodifiableIterable(numbers);
        final Iterator<Integer> iterator = unmodifiableIterable.iterator();
        if (iterator.hasNext()) {
            iterator.remove();
        }
    }

}
