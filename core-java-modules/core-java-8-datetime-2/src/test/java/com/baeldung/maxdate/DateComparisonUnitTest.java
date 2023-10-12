package com.baeldung.maxdate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateComparisonUnitTest {

    @Test
    public void whenCompareTodayWithMaxDate_thenCorrectResult() {
        DateComparison comparator = new DateComparison();
        int result = comparator.compareTodayWithMaxDate();

        assertTrue(result < 0);
    }
}