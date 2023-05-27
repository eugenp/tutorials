package com.baeldung.date;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DayOfTheWeekUnitTest {

    DayOfTheWeek dayOfTheWeek = new DayOfTheWeek();

    @Test
    void whenInputDateIsMonday_ThenFirstDayOfWeekIsSameAsInputDay() {
        TestData testData = new TestData(LocalDate.of(2023, 2, 20),LocalDate.of(2023,2,20) );
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingISODayOfWeek(testData.inputDate));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingCalendar(testData.inputDate));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingDayOfWeek(testData.inputDate));
    }

    @Test
    void whenInputDateIsSunday_ThenFirstDayOfWeekIsPreviousWeekMonday() {
        TestData testData = new TestData(LocalDate.of(2023, 2, 19), LocalDate.of(2023,2,13));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingISODayOfWeek(testData.inputDate));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingCalendar(testData.inputDate));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingDayOfWeek(testData.inputDate));
    }

    @Test
    void whenInputDateIsNewYearMidWeek_ThenFirstDayOfWeekIsPreviousYearLastMonday() {
        TestData testData = new TestData(LocalDate.of(2022, 1, 1), LocalDate.of(2021,12,27));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingISODayOfWeek(testData.inputDate));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingCalendar(testData.inputDate));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingDayOfWeek(testData.inputDate));
     }

    @Test
    void whenInputDataHasLocale_ThenFirstDayOfWeekIsMondayOrSundayInDifferentLocale() {
        List<TestData> testData = Arrays.asList(
                new TestData(LocalDate.of(2023, 2, 20),Locale.FRANCE,LocalDate.of(2023,2,20)),
                new TestData(LocalDate.of(2023, 2, 19),Locale.FRANCE,LocalDate.of(2023,2,13)),
                new TestData(LocalDate.of(2022, 1, 1),Locale.FRANCE,LocalDate.of(2021,12,27)),
                // day of week starts from sunday in USA
                new TestData(LocalDate.of(2023, 2, 20),Locale.US,LocalDate.of(2023,2,19)),
                new TestData(LocalDate.of(2023, 2, 19),Locale.US,LocalDate.of(2023,2,19)),
                new TestData(LocalDate.of(2022, 1, 1),Locale.US,LocalDate.of(2021,12,26))
        );
        testData.forEach(data -> Assertions.assertEquals(data.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingLocale(data.inputDate,data.inputLocale)));
    }

    private static class TestData{
        private final LocalDate inputDate;
        private Locale inputLocale;
        private final LocalDate expectedOutput;

        private TestData(LocalDate inputDate, LocalDate expectedOutput){
            this.inputDate = inputDate;
            this.expectedOutput = expectedOutput;
        }

        private TestData(LocalDate inputDate, Locale inputLocale, LocalDate expectedOutput){
            this.inputDate = inputDate;
            this.inputLocale = inputLocale;
            this.expectedOutput = expectedOutput;
        }
    }
}