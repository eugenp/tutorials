package com.baeldung.maxdate;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DateComparisonUnitTest {

  @Test
  void whenCompareTodayWithMaxDate_thenCorrectResult() {
    DateComparison comparator = new DateComparison();
    int result = comparator.compareTodayWithMaxDate();

    assertTrue(result < 0);
  }
}
