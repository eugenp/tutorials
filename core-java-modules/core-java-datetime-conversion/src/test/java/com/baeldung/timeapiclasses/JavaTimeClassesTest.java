import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class JavaTimeClassesTest {
    final Logger logger = Logger.getLogger(JavaTimeClassesTest.class.getName());

    @Test
    void givenCurrentDateTime_whenUsingLocalDateTime_thenCorrect() {
        LocalDate currentDate = LocalDate.now(); // Current date
        LocalTime currentTime = LocalTime.now(); // Current time
        LocalDateTime currentDateTime = LocalDateTime.now(); // Current date and time

        assertThat(currentDate).isBeforeOrEqualTo(LocalDate.now());
        assertThat(currentTime).isBeforeOrEqualTo(LocalTime.now());
        assertThat(currentDateTime).isBeforeOrEqualTo(LocalDateTime.now());
    }


    @Test
    void givenSpecificDateTime_whenUsingLocalDateTime_thenCorrect() {
        LocalDate date = LocalDate.of(2024, Month.SEPTEMBER, 18);
        LocalTime time = LocalTime.of(10, 30);
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        assertEquals("2024-09-18", date.toString());
        assertEquals("10:30", time.toString());
        assertEquals("2024-09-18T10:30", dateTime.toString());
    }


    @Test
    void givenTodaysDate_whenUsingVariousTemporalAdjusters_thenReturnCorrectAdjustedDates() {
        LocalDate today = LocalDate.now();

        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        assertThat(nextMonday.getDayOfWeek())
                .as("Next Monday should be correctly identified")
                .isEqualTo(DayOfWeek.MONDAY);

        LocalDate firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        assertThat(firstDayOfMonth.getDayOfMonth())
                .as("First day of the month should be 1")
                .isEqualTo(1);
    }

    @Test
    void givenCustomTemporalAdjuster_whenAddingTenDays_thenCorrect() {
        LocalDate today = LocalDate.now();
        TemporalAdjuster addTenDays = temporal -> temporal.plus(10, ChronoUnit.DAYS);
        LocalDate adjustedDate = today.with(addTenDays);

        assertEquals(today.plusDays(10), adjustedDate, "The adjusted date should be 10 days later than today");
    }


    @Test
    void givenDateTimeFormat_whenFormattingAndParsing_thenVerifyResults() {
        // given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime specificDateTime = LocalDateTime.of(2024, 9, 18, 10, 30);
        // when
        String formattedDate = specificDateTime.format(formatter);
        LocalDateTime parsedDateTime = LocalDateTime.parse("18-09-2024 10:30", formatter);
        // then
        assertThat(formattedDate).isNotEmpty().isEqualTo("18-09-2024 10:30");
        assertThat(parsedDateTime).isEqualTo(specificDateTime).satisfies(dt -> {
            assertThat(dt.getYear()).isEqualTo(2024);
            assertThat(dt.getMonthValue()).isEqualTo(9);
            assertThat(dt.getDayOfMonth()).isEqualTo(18);
            assertThat(dt.getHour()).isEqualTo(10);
            assertThat(dt.getMinute()).isEqualTo(30);
            assertThat(dt.getSecond()).isZero();
        });
    }

    @Test
    void givenVariousTimeZones_whenCreatingOffsetDateTime_thenVerifyOffsets() {
        // given
        ZoneId parisZone = ZoneId.of("Europe/Paris");
        ZoneId tokyoZone = ZoneId.of("Asia/Tokyo");
        ZoneId nyZone = ZoneId.of("America/New_York");
        // when
        OffsetDateTime parisTime = OffsetDateTime.now(parisZone);
        OffsetDateTime tokyoTime = OffsetDateTime.now(tokyoZone);
        OffsetDateTime nyTime = OffsetDateTime.now(nyZone);
        // then
        assertThat(parisTime)
                .isNotNull()
                .satisfies(time -> {
                    assertThat(time.getOffset().getTotalSeconds())
                            .isEqualTo(parisZone.getRules().getOffset(Instant.now()).getTotalSeconds());
                    assertThat(time.getHour()).isBetween(0, 23);
                });
        // Verify time differences between zones
        assertThat(ChronoUnit.HOURS.between(nyTime, parisTime) % 24)
                .isGreaterThanOrEqualTo(5)  // NY is typically 5-6 hours behind Paris
                .isLessThanOrEqualTo(7);
        assertThat(ChronoUnit.HOURS.between(parisTime, tokyoTime) % 24)
                .isGreaterThanOrEqualTo(6)  // Tokyo is typically 7-8 hours ahead of Paris
                .isLessThanOrEqualTo(8);
    }

    @Test
    void givenSystemClock_whenComparingDifferentTimeZones_thenVerifyRelationships() {
        // given
        Clock nyClock = Clock.system(ZoneId.of("America/New_York"));
        // when
        LocalDateTime nyTime = LocalDateTime.now(nyClock);
        // then
        assertThat(nyTime)
                .isNotNull()
                .satisfies(time -> {
                    assertThat(time.getHour()).isBetween(0, 23);
                    assertThat(time.getMinute()).isBetween(0, 59);
                    // Verify it's within last minute (recent)
                    assertThat(time).isCloseTo(
                            LocalDateTime.now(),
                            within(1, ChronoUnit.MINUTES)
                    );
                });
    }

    @Test
    void givenLegacyDate_whenConvertingToInstant_thenCorrect() {
        Date legacyDate = new Date();
        Instant instant = legacyDate.toInstant();

        assertThat(instant).isNotNull();
        logger.info("Instant: " + instant); // e.g., 2024-09-24T17:30:45Z
    }


    @Test
    void givenCalendar_whenConvertingToZonedDateTime_thenCorrect() {
        Calendar calendar = Calendar.getInstance();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());

        assertThat(zonedDateTime).isNotNull();
        logger.info("ZonedDateTime: " + zonedDateTime); // e.g., 2024-09-24T10:30:45-07:00[America/Los_Angeles]
    }

}

