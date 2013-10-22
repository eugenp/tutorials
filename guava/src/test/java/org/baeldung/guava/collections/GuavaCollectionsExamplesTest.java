package org.baeldung.guava.collections;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class GuavaCollectionsExamplesTest {

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
    public final void givenNoSearchResult_whenFindingElementInIterable_thenNoException() {
        final Iterable<String> theCollection = Sets.newHashSet("abcd", "efgh", "ijkl");

        final String found = Iterables.find(theCollection, new Predicate<String>() {
            @Override
            public final boolean apply(final String input) {
                return input.length() == 1;
            }
        });

        assertNull(found);
    }

}
