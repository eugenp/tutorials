import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

public class JavaTimeClassesTest {

    @Test
    void givenTodaysDate_whenUsingVariousTemporalAdjusters_thenReturnCorrectAdjustedDates() {
        LocalDate today = LocalDate.now();

        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        assertEquals(DayOfWeek.MONDAY, nextMonday.getDayOfWeek(), "Next Monday should be correctly identified");

        LocalDate firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        assertEquals(1, firstDayOfMonth.getDayOfMonth(), "First day of the month should be 1");

        LocalDate lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(lastDayOfMonth.lengthOfMonth(), lastDayOfMonth.getDayOfMonth(), "Last day of the month should match the length of the month");

        LocalDate previousFriday = today.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        assertEquals(DayOfWeek.FRIDAY, previousFriday.getDayOfWeek(), "Previous Friday should be correctly identified");

        LocalDate nextTuesday = today.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
        assertEquals(DayOfWeek.TUESDAY, nextTuesday.getDayOfWeek(), "Next Tuesday should be correctly identified");

        TemporalAdjuster addTenDays = temporal -> temporal.plus(10, ChronoUnit.DAYS);
        LocalDate adjustedDate = today.with(addTenDays);
        assertEquals(today.plusDays(10), adjustedDate, "The adjusted date should be 10 days later than today");

        TemporalAdjuster adjuster = TemporalAdjusters.firstDayOfNextMonth();
        LocalDate adjustedDateDirect = (LocalDate) adjuster.adjustInto(today);
        assertEquals(today.plusMonths(1)
            .withDayOfMonth(1), adjustedDateDirect, "The adjusted date should be the first day of next month");
    }

    @Test
    void givenYear2024_whenCheckingLeapYear_thenReturnTrue() {
        Year year = Year.of(2024);

        assertTrue(year.isLeap(), "2024 should be a leap year");
    }

    @Test
    void givenFebruary2024_whenGettingDaysInMonth_thenReturn29Days() {
        YearMonth yearMonth = YearMonth.of(2024, 2); // February 2024

        assertEquals(29, yearMonth.lengthOfMonth(), "February 2024 should have 29 days");
    }

    @Test
    void givenBirthdayOnMarch25_whenCheckingToday_thenReturnNotBirthday() {
        MonthDay birthday = MonthDay.of(3, 25); // March 25
        MonthDay today = MonthDay.from(LocalDate.now());

        assertNotEquals(today, birthday, "Today should not be the birthday");
    }

    @Test
    void givenTimeZoneAmericaNewYork_whenGettingCurrentTimeWithClock_thenShouldNotBeNull() {
        Clock clock = Clock.system(ZoneId.of("America/New_York"));
        LocalDateTime currentTime = LocalDateTime.now(clock);

        assertNotNull(currentTime, "Current time should not be null");
    }

    @Test
    void givenUTCOffsetMinus5_whenCreatingOffsetDateTime_thenShouldMatchOffset() {
        OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneId.of("Europe/Paris")); // UTC-5

        assertNotNull(offsetDateTime, "OffsetDateTime should not be null");
        assertEquals(2, offsetDateTime.getOffset()
            .getTotalSeconds() / 3600, "Offset should be UTC+2");
    }

    @Test
    void givenUTCOffsetPlus2_whenCreatingOffsetTime_thenShouldMatchOffset() {
        OffsetTime offsetTime = OffsetTime.now(ZoneOffset.ofHours(2)); // UTC+2

        assertNotNull(offsetTime, "OffsetTime should not be null");
        System.out.println(offsetTime);
        assertEquals(2, offsetTime.getOffset()
            .getTotalSeconds() / 3600, "Offset should be UTC+2");
    }

    @Test
    void givenDateTimeFormat_whenFormattingAndParsingDateTime_thenShouldMatchExpectedValue() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(formatter);

        assertNotNull(formattedDate, "Formatted date-time should not be null");

        // Parsing a date-time string
        LocalDateTime parsedDateTime = LocalDateTime.parse("18-09-2024 10:30", formatter);
        assertEquals(LocalDate.of(2024, 9, 18)
            .atTime(10, 30), parsedDateTime, "Parsed date-time should match the expected value");
    }
}

