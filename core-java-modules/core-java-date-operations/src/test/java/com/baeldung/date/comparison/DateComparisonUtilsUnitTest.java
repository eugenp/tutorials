package com.baeldung.date.comparison;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;

public class DateComparisonUtilsUnitTest {

    private Date day1Morning = toDate(LocalDateTime.of(2019, 10, 19, 6, 30, 40));
    private Date day1Evening = toDate(LocalDateTime.of(2019, 10, 19, 18, 30, 50));
    private Date day2Morning = toDate(LocalDateTime.of(2019, 10, 20, 6, 30, 50));

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault())
            .toInstant());
    }

    @Test
    public void givenDatesWithDifferentTime_whenIsSameDay_thenReturnsTrue() {
        assertTrue(DateComparisonUtils.isSameDayUsingLocalDate(day1Morning, day1Evening));
        assertTrue(DateComparisonUtils.isSameDayUsingSimpleDateFormat(day1Morning, day1Evening));
        assertTrue(DateComparisonUtils.isSameDayUsingCalendar(day1Morning, day1Evening));
        assertTrue(DateComparisonUtils.isSameDayUsingApacheCommons(day1Morning, day1Evening));
        assertTrue(DateComparisonUtils.isSameDayUsingJoda(day1Morning, day1Evening));
        assertTrue(DateComparisonUtils.isSameDayUsingDate4j(day1Morning, day1Evening));
    }

    @Test
    public void givenDates_whenIsDifferentDay_thenReturnsFalse() {
        assertFalse(DateComparisonUtils.isSameDayUsingLocalDate(day1Morning, day2Morning));
        assertFalse(DateComparisonUtils.isSameDayUsingLocalDate(day1Evening, day2Morning));

        assertFalse(DateComparisonUtils.isSameDayUsingSimpleDateFormat(day1Morning, day2Morning));
        assertFalse(DateComparisonUtils.isSameDayUsingSimpleDateFormat(day1Evening, day2Morning));

        assertFalse(DateComparisonUtils.isSameDayUsingCalendar(day1Morning, day2Morning));
        assertFalse(DateComparisonUtils.isSameDayUsingCalendar(day1Evening, day2Morning));

        assertFalse(DateComparisonUtils.isSameDayUsingApacheCommons(day1Morning, day2Morning));
        assertFalse(DateComparisonUtils.isSameDayUsingApacheCommons(day1Evening, day2Morning));

        assertFalse(DateComparisonUtils.isSameDayUsingJoda(day1Morning, day2Morning));
        assertFalse(DateComparisonUtils.isSameDayUsingJoda(day1Evening, day2Morning));

        assertFalse(DateComparisonUtils.isSameDayUsingDate4j(day1Morning, day2Morning));
        assertFalse(DateComparisonUtils.isSameDayUsingDate4j(day1Evening, day2Morning));
    }
}
