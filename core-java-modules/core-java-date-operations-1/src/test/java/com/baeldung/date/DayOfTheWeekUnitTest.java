package com.baeldung.date;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import lombok.Builder;
import lombok.Setter;

class DayOfTheWeekUnitTest {

    DayOfTheWeek dayOfTheWeek = new DayOfTheWeek();

    @Test
    void whenInputDateIsMonday_ThenFirstDayOfWeekIsSameAsInputDay() {
        TestData testData =
                TestData.builder().inputDate(LocalDate.of(2023, 2, 20)).expectedOutput(LocalDate.of(2023,2,20)).build();
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingISODayOfWeek(testData.inputDate));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingCalendar(testData.inputDate));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingDayOfWeek(testData.inputDate));
    }

    @Test
    void whenInputDateIsSunday_ThenFirstDayOfWeekIsPreviousWeekMonday() {
        TestData testData =
                TestData.builder().inputDate(LocalDate.of(2023, 2, 19)).expectedOutput(LocalDate.of(2023,2,13)).build();
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingISODayOfWeek(testData.inputDate));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingCalendar(testData.inputDate));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingDayOfWeek(testData.inputDate));
    }

    @Test
    void whenInputDateIsNewYearMidWeek_ThenFirstDayOfWeekIsPreviousYearLastMonday() {
        TestData testData =
                TestData.builder().inputDate(LocalDate.of(2022, 1, 1)).expectedOutput(LocalDate.of(2021,12,27)).build();
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingISODayOfWeek(testData.inputDate));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingCalendar(testData.inputDate));
        Assertions.assertEquals(testData.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingDayOfWeek(testData.inputDate));
     }

    @Test
    void whenInputDataHasLocale_ThenFirstDayOfWeekIsMondayOrSundayInDifferentLocale() {
        List<TestData> testData = Arrays.asList(
                TestData.builder().inputLocale(Locale.FRANCE).
                        inputDate(LocalDate.of(2023, 2, 20)).expectedOutput(LocalDate.of(2023,2,20)).build(),
                TestData.builder().inputLocale(Locale.FRANCE)
                        .inputDate(LocalDate.of(2023, 2, 19)).expectedOutput(LocalDate.of(2023,2,13)).build(),
                //Note that the first week may start in the previous calendar year.
                TestData.builder().inputLocale(Locale.FRANCE)
                        .inputDate(LocalDate.of(2022, 1, 1)).expectedOutput(LocalDate.of(2021,12,27)).build(),

                // week starts from Sunday in the US
                TestData.builder().inputLocale(Locale.US).
                        inputDate(LocalDate.of(2023, 2, 20)).expectedOutput(LocalDate.of(2023,2,19)).build(),
                TestData.builder().inputLocale(Locale.US)
                        .inputDate(LocalDate.of(2023, 2, 19)).expectedOutput(LocalDate.of(2023,2,19)).build(),
                //Note that the first week may start in the previous calendar year.
                TestData.builder().inputLocale(Locale.US)
                        .inputDate(LocalDate.of(2022, 1, 1)).expectedOutput(LocalDate.of(2021,12,26)).build()
        );
        testData.forEach(data -> Assertions.assertEquals(data.expectedOutput, dayOfTheWeek.getDateOfFirstDayOfTheWeek_UsingLocale(data.inputDate,data.inputLocale)));
    }

    @Builder
    @Setter
    private static class TestData{
        LocalDate inputDate;
        Locale inputLocale;
        LocalDate expectedOutput;
    }
}