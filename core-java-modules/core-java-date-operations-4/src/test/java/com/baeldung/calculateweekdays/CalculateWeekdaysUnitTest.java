package com.baeldung.calculateweekdays;

import static junit.framework.TestCase.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class CalculateWeekdaysUnitTest {

    // Start Saturday end following Sunday (answer is 0)
    LocalDate startTomorrow = LocalDate.of(2023, 12, 2);
    LocalDate endTomorrow = LocalDate.of(2023, 12, 3);

    // Three week gap with midweek start and finish (answer is 17)
    LocalDate startThreeWeeks = LocalDate.of(2023, 11, 28);
    LocalDate endThreeWeeks = LocalDate.of(2023, 12, 21);

    // Three week gap with midweek start and weekend finish (answer is 17)
    LocalDate startThreeWeeks2 = LocalDate.of(2023, 11, 6);
    LocalDate endThreeWeeks2 = LocalDate.of(2023, 12, 30);

    // Week gap start and end on weekend (answer is 40)
    LocalDate startThreeWeeksWeekend = LocalDate.of(2023, 12, 2);
    LocalDate endThreeWeeksWeekend = LocalDate.of(2023, 12, 9);

    @Test
    void givenTwoDaysOnSameWeekend_whenUsingStreams_thenCalculateWeekdays(){
        CalculateWeekdays c = new CalculateWeekdays();
        long result = c.getWorkingDaysWithStream(startTomorrow, endTomorrow);
        assertEquals(0, result);
    }

    @Test
    void givenTwoDaysOnSameWeekend_whenUsingMaths_thenCalculateWeekdays(){
        CalculateWeekdays c = new CalculateWeekdays();
        long result = c.getWorkingDaysWithoutStream(startTomorrow, endTomorrow);
        assertEquals(0, result);
    }

    @Test
    void givenAThreeWeekGapMidweekDates_whenUsingStreams_thenCalculateWeekdays(){
        CalculateWeekdays c = new CalculateWeekdays();
        long result = c.getWorkingDaysWithStream(startThreeWeeks, endThreeWeeks);
        assertEquals(17, result);
    }

    @Test
    void givenAThreeWeekGapMidweekDates_whenUsingMaths_thenCalculateWeekdays(){
        CalculateWeekdays c = new CalculateWeekdays();
        long result = c.getWorkingDaysWithoutStream(startThreeWeeks, endThreeWeeks);
        assertEquals(17, result);
    }

    @Test
    void givenThreeWeekGapMidweekAndWeekendDates_whenUsingStreams_thenCalculateWeekdays(){
        CalculateWeekdays c = new CalculateWeekdays();
        long result = c.getWorkingDaysWithStream(startThreeWeeksWeekend, endThreeWeeksWeekend);
        assertEquals(5, result);
    }

    @Test
    void givenThreeWeekGapMidweekAndWeekendDates_whenUsingMaths_thenCalculateWeekdays(){
        CalculateWeekdays c = new CalculateWeekdays();
        long result = c.getWorkingDaysWithoutStream(startThreeWeeksWeekend, endThreeWeeksWeekend);
        assertEquals(5, result);
    }

    @Test
    void givenThreeWeekGapWeekendDates_whenUsingStreams_thenCalculateWeekdays(){
        CalculateWeekdays c = new CalculateWeekdays();
        long result = c.getWorkingDaysWithStream(startThreeWeeks2, endThreeWeeks2);
        assertEquals(40, result);
    }

    @Test
    void givenThreeWeekGapWeekendDates_whenUsingMaths_thenCalculateWeekdays(){
        CalculateWeekdays c = new CalculateWeekdays();
        long result = c.getWorkingDaysWithoutStream(startThreeWeeks2, endThreeWeeks2);
        assertEquals(40, result);
    }

}
