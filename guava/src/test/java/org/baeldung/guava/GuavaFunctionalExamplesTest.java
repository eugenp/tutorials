package org.baeldung.guava;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class GuavaFunctionalExamplesTest {

    // tests

    // predicates

    @Test
    public final void whenFilteringNumbersAccordingToACondition_thenCorrectResults() {
        final List<Integer> randomNumbers = Lists.newArrayList(1, 2, 3, 6, 8, 10, 34, 57, 89);
        final Predicate<Integer> acceptEvenNumber = new Predicate<Integer>() {
            @Override
            public final boolean apply(final Integer number) {
                return (number % 2) == 0;
            }
        };
        final List<Integer> evenNumbers = Lists.newArrayList(Collections2.filter(randomNumbers, acceptEvenNumber));

        final Integer found = Collections.binarySearch(evenNumbers, 57);
        assertThat(found, lessThan(0));
    }

    @Test
    public final void givenEvenNumbers_whenCheckingIfAllSatisfyTheEvenPredicate_thenYes() {
        final List<Integer> eventNumbers = Lists.newArrayList(2, 6, 8, 10, 34, 90);
        final Predicate<Integer> acceptEvenNumber = new Predicate<Integer>() {
            @Override
            public final boolean apply(final Integer number) {
                return (number % 2) == 0;
            }
        };

        assertTrue(Iterables.all(eventNumbers, acceptEvenNumber));
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

}
