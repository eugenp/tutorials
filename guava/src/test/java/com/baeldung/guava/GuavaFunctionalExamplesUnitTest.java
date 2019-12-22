package com.baeldung.guava;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

public class GuavaFunctionalExamplesUnitTest {

    // tests

    // predicates - filtering

    @Test
    public final void whenFilteringNumbersAccordingToACondition_thenCorrectResults() {
        final List<Integer> numbers = Lists.newArrayList(1, 2, 3, 6, 8, 10, 34, 57, 89);
        final Predicate<Integer> acceptEvenNumber = new Predicate<Integer>() {
            @Override
            public final boolean apply(final Integer number) {
                return (number % 2) == 0;
            }
        };
        final List<Integer> evenNumbers = Lists.newArrayList(Collections2.filter(numbers, acceptEvenNumber));

        final Integer found = Collections.binarySearch(evenNumbers, 57);
        assertThat(found, lessThan(0));
    }

    @Test
    public final void givenCollectionContainsNulls_whenNullsAreFilteredOut_thenResultingCollectionsHasNoNulls() {
        final List<String> withNulls = Lists.newArrayList("a", "bc", null, "def");
        final Iterable<String> withoutNuls = Iterables.filter(withNulls, Predicates.notNull());

        assertTrue(Iterables.all(withoutNuls, Predicates.notNull()));
    }

    // predicates - checking

    @Test
    public final void givenEvenNumbers_whenCheckingIfAllSatisfyTheEvenPredicate_thenYes() {
        final List<Integer> evenNumbers = Lists.newArrayList(2, 6, 8, 10, 34, 90);
        final Predicate<Integer> acceptEvenNumber = new Predicate<Integer>() {
            @Override
            public final boolean apply(final Integer number) {
                return (number % 2) == 0;
            }
        };

        assertTrue(Iterables.all(evenNumbers, acceptEvenNumber));
    }

    // negating a predicate

    @Test
    public final void givenCollectionOfEvenNumbers_whenCheckingThatCollectionContainsNoOddNumber_thenTrue() {
        final List<Integer> evenNumbers = Lists.newArrayList(2, 6, 8, 10, 34, 90);
        final Predicate<Integer> acceptOddNumber = new Predicate<Integer>() {
            @Override
            public final boolean apply(final Integer number) {
                return (number % 2) != 0;
            }
        };

        assertTrue(Iterables.all(evenNumbers, Predicates.not(acceptOddNumber)));
    }

    // other predicates

    @Test
    public final void when_thenCorrect() {
        // CharMatcher.forPredicate(predicate)
    }

    // functions

    @Test
    public final void whenApplyingSimpleFunctionToInputs_thenCorrectlyTransformed() {
        final List<Integer> numbers = Lists.newArrayList(1, 2, 3);
        final List<String> numbersAsStrings = Lists.transform(numbers, Functions.toStringFunction());
        assertThat(numbersAsStrings, contains("1", "2", "3"));
    }

    @Test
    public final void whenUsingAnIntermediaryFunctionToOrder_thenCorerctlyOrderedInAlphabeticalOrder() {
        final List<Integer> numbersToSort = Arrays.asList(2, 1, 11, 100, 8, 14);
        final Ordering<Object> ordering = Ordering.natural().onResultOf(Functions.toStringFunction());
        final List<Integer> inAlphabeticalOrder = ordering.sortedCopy(numbersToSort);

        final List<Integer> correctAlphabeticalOrder = Lists.newArrayList(1, 100, 11, 14, 2, 8);
        assertThat(correctAlphabeticalOrder, equalTo(inAlphabeticalOrder));
    }

    @Test
    public final void whenChainingPredicatesAndFunctions_thenCorrectResults() {
        final List<Integer> numbers = Arrays.asList(2, 1, 11, 100, 8, 14);
        final Predicate<Integer> acceptEvenNumber = new Predicate<Integer>() {
            @Override
            public final boolean apply(final Integer number) {
                return (number % 2) == 0;
            }
        };
        final Function<Integer, Integer> powerOfTwo = new Function<Integer, Integer>() {
            @Override
            public final Integer apply(final Integer input) {
                return (int) Math.pow(input, 2);
            }
        };

        final FluentIterable<Integer> powerOfTwoOnlyForEvenNumbers = FluentIterable.from(numbers).filter(acceptEvenNumber).transform(powerOfTwo);
        assertThat(powerOfTwoOnlyForEvenNumbers, contains(4, 10000, 64, 196));
    }

    @Test
    public final void whenUsingFunctionComposition_thenCorrectResults() {
        final List<Integer> numbers = Arrays.asList(2, 3);
        final Function<Integer, Integer> powerOfTwo = new Function<Integer, Integer>() {
            @Override
            public final Integer apply(final Integer input) {
                return (int) Math.pow(input, 2);
            }
        };

        final List<Integer> result = Lists.transform(numbers, Functions.compose(powerOfTwo, powerOfTwo));
        assertThat(result, contains(16, 81));
    }

    // Set+Function => Map

    /**
     * - see: http://code.google.com/p/guava-libraries/issues/detail?id=56
     */
    @Test
    public final void whenMapIsBackedBySetAndFunction_thenCorrect() {
        final Function<Integer, Integer> powerOfTwo = new Function<Integer, Integer>() {
            @Override
            public final Integer apply(final Integer input) {
                return (int) Math.pow(input, 2);
            }
        };
        final Set<Integer> lowNumbers = Sets.newHashSet(2, 3, 4);

        final Map<Integer, Integer> numberToPowerOfTwoMuttable = Maps.asMap(lowNumbers, powerOfTwo);
        final Map<Integer, Integer> numberToPowerOfTwoImuttable = Maps.toMap(lowNumbers, powerOfTwo);
        assertThat(numberToPowerOfTwoMuttable.get(2), equalTo(4));
        assertThat(numberToPowerOfTwoImuttable.get(2), equalTo(4));
    }

    // Predicate => Function

    @Test
    public final void whenConvertingPredicateToFunction_thenCorrect() {
        final List<Integer> numbers = Lists.newArrayList(1, 2, 3, 6);
        final Predicate<Integer> acceptEvenNumber = new Predicate<Integer>() {
            @Override
            public final boolean apply(final Integer number) {
                return (number % 2) == 0;
            }
        };
        final Function<Integer, Boolean> isEventNumberFunction = Functions.forPredicate(acceptEvenNumber);
        final List<Boolean> areNumbersEven = Lists.transform(numbers, isEventNumberFunction);

        assertThat(areNumbersEven, contains(false, true, false, true));
    }

}
