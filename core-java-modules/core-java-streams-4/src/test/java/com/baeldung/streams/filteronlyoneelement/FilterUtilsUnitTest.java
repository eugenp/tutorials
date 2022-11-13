package com.baeldung.streams.filteronlyoneelement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class FilterUtilsUnitTest {

    private static final Predicate<Integer> IS_STRICTLY_GREATER_THAN5 = i -> i > 5;
    private static final Predicate<Integer> IS_STRICTLY_GREATER_THAN4 = i -> i > 4;
    private static final Predicate<Integer> IS_STRICTLY_GREATER_THAN3 = i -> i > 3;

    private Stream getIntegers() {
        return Stream.of(1, 2, 3, 4, 5);
    }

    @Test
    void givenNoElementMatchingPredicate_WhenFindUniqueElementMatchingPredicateWithReduction_ThenNoneFound() {
        assertTrue(FilterUtils.findUniqueElementMatchingPredicate_WithReduction(getIntegers(), IS_STRICTLY_GREATER_THAN5)
            .isEmpty());
    }

    @Test
    void givenTwoElementsMatchingPredicate_WhenFindUniqueElementMatchingPredicateWithReduction_ThenEmpty() {
        assertTrue(FilterUtils.findUniqueElementMatchingPredicate_WithReduction(getIntegers(), IS_STRICTLY_GREATER_THAN3)
            .isEmpty());
    }

    @Test
    void givenOnlyOneElementMatchingPredicate_WhenFindUniqueElementMatchingPredicateWithReduction_ThenFindsIt() {
        assertEquals(5, FilterUtils.findUniqueElementMatchingPredicate_WithReduction(getIntegers(), IS_STRICTLY_GREATER_THAN4)
            .get());
    }

    @Test
    void givenNoElementMatchingPredicate_WhenGetUniqueElementMatchingPredicateWithReduction_ThenThrows() {
        assertThrows(IllegalStateException.class, () -> FilterUtils.getUniqueElementMatchingPredicate_WithReduction(getIntegers(), IS_STRICTLY_GREATER_THAN5));
    }

    @Test
    void givenTwoElementsMatchingPredicate_WhenGetUniqueElementMatchingPredicateWithReduction_ThenThrows() {
        assertThrows(IllegalStateException.class, () -> FilterUtils.getUniqueElementMatchingPredicate_WithReduction(getIntegers(), IS_STRICTLY_GREATER_THAN3));
    }

    @Test
    void givenOnlyOneElementMatchingPredicate_WhenFindUniqueElementMatchingPredicateWithReduction_ThenGetIt() {
        assertEquals(5, FilterUtils.getUniqueElementMatchingPredicate_WithReduction(getIntegers(), IS_STRICTLY_GREATER_THAN4));
    }

    @Test
    void givenNoElementMatchingPredicate_WhenFindUniqueElementMatchingPredicateWithCollectingAndThen_ThenEmpty() {
        assertTrue(FilterUtils.findUniqueElementMatchingPredicate_WithCollectingAndThen(getIntegers(), IS_STRICTLY_GREATER_THAN5)
            .isEmpty());
    }

    @Test
    void givenTwoElementsMatchingPredicate_WhenFindUniqueElementMatchingPredicateWithCollectingAndThen_ThenEmpty() {
        assertTrue(FilterUtils.findUniqueElementMatchingPredicate_WithCollectingAndThen(getIntegers(), IS_STRICTLY_GREATER_THAN3)
            .isEmpty());
    }

    @Test
    void givenOnlyOneElementMatchingPredicate_WhenFindUniqueElementMatchingPredicateWithCollectingAndThen_ThenFindsIt() {
        assertEquals(5, FilterUtils.findUniqueElementMatchingPredicate_WithCollectingAndThen(getIntegers(), IS_STRICTLY_GREATER_THAN4)
            .get());
    }

    @Test
    void givenNoElementMatchingPredicate_WhenGetUniqueElementMatchingPredicateWithCollectingAndThen_ThenThrows() {
        assertThrows(IllegalStateException.class, () -> FilterUtils.getUniqueElementMatchingPredicate_WithCollectingAndThen(getIntegers(), IS_STRICTLY_GREATER_THAN5));
    }

    @Test
    void givenTwoElementsMatchingPredicate_WhenGetUniqueElementMatchingPredicateWithCollectingAndThen_ThenThrows() {
        assertThrows(IllegalStateException.class, () -> FilterUtils.getUniqueElementMatchingPredicate_WithCollectingAndThen(getIntegers(), IS_STRICTLY_GREATER_THAN3));
    }

    @Test
    void givenOnlyOneElementMatchingPredicate_WhenFindUniqueElementMatchingPredicateWithCollectingAndThen_ThenGetIt() {
        assertEquals(5, FilterUtils.getUniqueElementMatchingPredicate_WithCollectingAndThen(getIntegers(), IS_STRICTLY_GREATER_THAN4));
    }

}
