package com.baeldung.multipleorwithif;

import static java.time.Month.DECEMBER;
import static java.time.Month.NOVEMBER;
import static java.time.Month.OCTOBER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Month;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

class MultipleOrWithIfUnitTest {

    private final Random rand = new Random();

    final Set<Month> months = Set.of(OCTOBER, NOVEMBER, DECEMBER);

    @Test
    public void givenIfStatement_whenMultipleOrOperator_thenAssert() {
        assertTrue(multipleOrOperatorIf(monthIn()));
        assertFalse(multipleOrOperatorIf(monthNotIn()));
    }

    boolean multipleOrOperatorIf(Month month) {
        if (month == OCTOBER || month == NOVEMBER || month == DECEMBER) {
            return true;
        }
        return false;
    }

    @Test
    public void givenSwitch_whenMultipleCase_thenBreakAndAssert() {
        assertTrue(switchMonth(monthIn()));
        assertFalse(switchMonth(monthNotIn()));
    }

    boolean switchMonth(Month month) {
        return switch (month) {
            case OCTOBER, NOVEMBER, DECEMBER -> true;
            default -> false;
        };
    }

    @Test
    public void givenAllowedValuesList_whenContains_thenAssert() {
        assertTrue(contains(monthIn()));
        assertFalse(contains(monthNotIn()));
    }

    @Test
    public void givenPredicates_whenTestMultipleOr_thenAssert() {
        assertTrue(predicateWithIf(monthIn()));
        assertFalse(predicateWithIf(monthNotIn()));
    }

    @Test
    public void givenInputList_whenFilterWithPredicate_thenAssert() {

        List<Month> list = List.of(monthIn(), monthIn(), monthNotIn());

        list.stream()
          .filter(this::predicateWithIf)
          .forEach(m -> assertThat(m, is(in(months))));
    }

    Predicate<Month> orPredicate() {
        Predicate<Month> predicate = x -> x == OCTOBER;
        Predicate<Month> predicate1 = x -> x == NOVEMBER;
        Predicate<Month> predicate2 = x -> x == DECEMBER;

        return predicate.or(predicate1)
          .or(predicate2);
    }

    boolean predicateWithIf(Month month) {
        if (orPredicate().test(month)) {
            return true;
        }
        return false;
    }

    @Test
    public void givenContainsInSetPredicate_whenTestPredicate_thenAssert() {
        Predicate<Month> collectionPredicate = this::contains;

        assertTrue(collectionPredicate.test(monthIn()));
        assertFalse(collectionPredicate.test(monthNotIn()));
    }

    @Test
    public void givenInputList_whenFilterWithContains_thenAssert() {

        List<Month> monthList = List.of(monthIn(), monthIn(), monthNotIn());

        monthList.stream()
          .filter(this::contains)
          .forEach(m -> assertThat(m, is(in(months))));
    }

    private boolean contains(Month month) {
        if (months.contains(month)) {
            return true;
        }
        return false;
    }

    private Month monthIn() {
        return Month.of(rand.ints(10, 13)
          .findFirst()
          .orElse(10));
    }

    private Month monthNotIn() {
        return Month.of(rand.ints(1, 10)
          .findFirst()
          .orElse(1));
    }
}