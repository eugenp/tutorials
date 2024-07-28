package com.baeldung.daterangeoverlap;

import org.joda.time.DateTime;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateRangeOverlapCheckerUnitTest {

    @Test
    public void givenPartialOverlappingRanges_thenReturnsTrue() {
        Calendar start1 = Calendar.getInstance();
        start1.clear();
        start1.set(2024, Calendar.DECEMBER, 15);
        Calendar end1 = Calendar.getInstance();
        end1.clear();
        end1.set(2024, Calendar.DECEMBER, 20);

        Calendar start2 = Calendar.getInstance();
        start2.clear();
        start2.set(2024, Calendar.DECEMBER, 18);
        Calendar end2 = Calendar.getInstance();
        end2.clear();
        end2.set(2024, Calendar.DECEMBER, 22);

        LocalDate startLD1 = LocalDate.of(2024, 12, 15);
        LocalDate endLD1 = LocalDate.of(2024, 12, 20);

        LocalDate startLD2 = LocalDate.of(2024, 12, 18);
        LocalDate endLD2 = LocalDate.of(2024, 12, 22);

        DateTime startJT1 = new DateTime(2024, 12, 15, 0, 0);
        DateTime endJT1 = new DateTime(2024, 12, 20, 0, 0);

        DateTime startJT2 = new DateTime(2024, 12, 18, 0, 0);
        DateTime endJT2 = new DateTime(2024, 12, 22, 0, 0);

        assertTrue(DateRangeOverlapChecker.isOverlapUsingCalendarAndDuration(start1, end1, start2, end2));
        assertTrue(DateRangeOverlapChecker.isOverlapUsingLocalDateAndDuration(startLD1, endLD1, startLD2, endLD2));

        assertTrue(DateRangeOverlapChecker.isOverlapUsingCalendarAndCondition(start1, end1, start2, end2));
        assertTrue(DateRangeOverlapChecker.isOverlapUsingLocalDateAndCondition(startLD1, endLD1, startLD2, endLD2));

        assertTrue(DateRangeOverlapChecker.isOverlapUsingCalendarAndFindMin(start1, end1, start2, end2));
        assertTrue(DateRangeOverlapChecker.isOverlapUsingLocalDateAndFindMin(startLD1, endLD1, startLD2, endLD2));

        assertTrue(DateRangeOverlapChecker.isOverlapUsingJodaTime(startJT1, endJT1, startJT2, endJT2));
    }

    @Test
    public void givenFullOverlappingRanges_thenReturnsTrue() {
        Calendar start1 = Calendar.getInstance();
        start1.clear();
        start1.set(2024, Calendar.DECEMBER, 15);
        Calendar end1 = Calendar.getInstance();
        end1.clear();
        end1.set(2024, Calendar.DECEMBER, 20);

        Calendar start2 = Calendar.getInstance();
        start2.set(2024, Calendar.DECEMBER, 16);
        Calendar end2 = Calendar.getInstance();
        end2.set(2024, Calendar.DECEMBER, 18);

        LocalDate startLD1 = LocalDate.of(2024, 12, 15);
        LocalDate endLD1 = LocalDate.of(2024, 12, 20);

        LocalDate startLD2 = LocalDate.of(2024, 12, 16);
        LocalDate endLD2 = LocalDate.of(2024, 12, 18);

        DateTime startJT1 = new DateTime(2024, 12, 15, 0, 0);
        DateTime endJT1 = new DateTime(2024, 12, 20, 0, 0);

        DateTime startJT2 = new DateTime(2024, 12, 16, 0, 0);
        DateTime endJT2 = new DateTime(2024, 12, 18, 0, 0);

        assertTrue(DateRangeOverlapChecker.isOverlapUsingCalendarAndDuration(start1, end1, start2, end2));
        assertTrue(DateRangeOverlapChecker.isOverlapUsingLocalDateAndDuration(startLD1, endLD1, startLD2, endLD2));

        assertTrue(DateRangeOverlapChecker.isOverlapUsingCalendarAndCondition(start1, end1, start2, end2));
        assertTrue(DateRangeOverlapChecker.isOverlapUsingLocalDateAndCondition(startLD1, endLD1, startLD2, endLD2));

        assertTrue(DateRangeOverlapChecker.isOverlapUsingCalendarAndFindMin(start1, end1, start2, end2));
        assertTrue(DateRangeOverlapChecker.isOverlapUsingLocalDateAndFindMin(startLD1, endLD1, startLD2, endLD2));

        assertTrue(DateRangeOverlapChecker.isOverlapUsingJodaTime(startJT1, endJT1, startJT2, endJT2));
    }

    @Test
    public void givenConsecutiveRanges_thenReturnsFalse() {
        Calendar start1 = Calendar.getInstance();
        start1.clear();
        start1.set(2024, Calendar.DECEMBER, 15);
        Calendar end1 = Calendar.getInstance();
        end1.clear();
        end1.set(2024, Calendar.DECEMBER, 20);

        Calendar start2 = Calendar.getInstance();
        start2.clear();
        start2.set(2024, Calendar.DECEMBER, 21);
        Calendar end2 = Calendar.getInstance();
        end2.clear();
        end2.set(2024, Calendar.DECEMBER, 24);

        LocalDate startLD1 = LocalDate.of(2024, 12, 15);
        LocalDate endLD1 = LocalDate.of(2024, 12, 20);

        LocalDate startLD2 = LocalDate.of(2024, 12, 21);
        LocalDate endLD2 = LocalDate.of(2024, 12, 24);

        DateTime startJT1 = new DateTime(2024, 12, 15, 0, 0);
        DateTime endJT1 = new DateTime(2024, 12, 20, 0, 0);

        DateTime startJT2 = new DateTime(2024, 12, 21, 0, 0);
        DateTime endJT2 = new DateTime(2024, 12, 24, 0, 0);

        assertFalse(DateRangeOverlapChecker.isOverlapUsingCalendarAndDuration(start1, end1, start2, end2));
        assertFalse(DateRangeOverlapChecker.isOverlapUsingLocalDateAndDuration(startLD1, endLD1, startLD2, endLD2));

        assertFalse(DateRangeOverlapChecker.isOverlapUsingCalendarAndCondition(start1, end1, start2, end2));
        assertFalse(DateRangeOverlapChecker.isOverlapUsingLocalDateAndCondition(startLD1, endLD1, startLD2, endLD2));

        assertFalse(DateRangeOverlapChecker.isOverlapUsingCalendarAndFindMin(start1, end1, start2, end2));
        assertFalse(DateRangeOverlapChecker.isOverlapUsingLocalDateAndFindMin(startLD1, endLD1, startLD2, endLD2));

        assertFalse(DateRangeOverlapChecker.isOverlapUsingJodaTime(startJT1, endJT1, startJT2, endJT2));
    }

    @Test
    public void givenZeroRangeRanges_thenReturnsTrue() {
        Calendar start1 = Calendar.getInstance();
        start1.clear();
        start1.set(2024, Calendar.DECEMBER, 15);
        Calendar end1 = Calendar.getInstance();
        end1.clear();
        end1.set(2024, Calendar.DECEMBER, 20);

        Calendar start2 = Calendar.getInstance();
        start2.clear();
        start2.set(2024, Calendar.DECEMBER, 20);
        Calendar end2 = Calendar.getInstance();
        end2.clear();
        end2.set(2024, Calendar.DECEMBER, 20);

        LocalDate startLD1 = LocalDate.of(2024, 12, 15);
        LocalDate endLD1 = LocalDate.of(2024, 12, 20);
        LocalDate startLD2 = LocalDate.of(2024, 12, 20);
        LocalDate endLD2 = LocalDate.of(2024, 12, 20);

        DateTime startJT1 = new DateTime(2024, 12, 15, 0, 0);
        DateTime endJT1 = new DateTime(2024, 12, 20, 0, 0);
        DateTime startJT2 = new DateTime(2024, 12, 20, 0, 0);
        DateTime endJT2 = new DateTime(2024, 12, 20, 0, 0);

        assertTrue(DateRangeOverlapChecker.isOverlapUsingCalendarAndDuration(start1, end1, start2, end2));
        assertTrue(DateRangeOverlapChecker.isOverlapUsingLocalDateAndDuration(startLD1, endLD1, startLD2, endLD2));

        assertTrue(DateRangeOverlapChecker.isOverlapUsingCalendarAndCondition(start1, end1, start2, end2));
        assertTrue(DateRangeOverlapChecker.isOverlapUsingLocalDateAndCondition(startLD1, endLD1, startLD2, endLD2));

        assertTrue(DateRangeOverlapChecker.isOverlapUsingCalendarAndFindMin(start1, end1, start2, end2));
        assertTrue(DateRangeOverlapChecker.isOverlapUsingLocalDateAndFindMin(startLD1, endLD1, startLD2, endLD2));

        //the overlaps method considers two intervals as overlapping only if they have a non-zero duration.
        assertFalse(DateRangeOverlapChecker.isOverlapUsingJodaTime(startJT1, endJT1, startJT2, endJT2));
    }

}
