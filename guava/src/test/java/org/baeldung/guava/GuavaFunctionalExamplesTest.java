package org.baeldung.guava;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class GuavaFunctionalExamplesTest {

    // tests

    // predicates

    @Test
    public final void whenFilteringStringsAccordingToACondition_thenCorrectResults() {
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

}
