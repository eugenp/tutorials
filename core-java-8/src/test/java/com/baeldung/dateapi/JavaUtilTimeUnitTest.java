package com.baeldung.dateapi;

import org.junit.Test;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaUtilTimeUnitTest {

    @Test
    public void currentTime() {
        final LocalDate now = LocalDate.now();

        System.out.println(now);
        // there is not much to test here
    }

    @Test
    public void specificTime() {
        LocalDate birthDay = LocalDate.of(1990, Month.DECEMBER, 15);

        System.out.println(birthDay);
        // there is not much to test here
    }

    @Test
    public void extractMonth() {
        Month month = LocalDate.of(1990, Month.DECEMBER, 15).getMonth();

        assertThat(month).isEqualTo(Month.DECEMBER);
    }

    @Test
    public void subtractTime() {
        LocalDateTime fiveHoursBefore = LocalDateTime.of(1990, Month.DECEMBER, 15, 15, 0).minusHours(5);

        assertThat(fiveHoursBefore.getHour()).isEqualTo(10);
    }

    @Test
    public void alterField() {
        LocalDateTime inJune = LocalDateTime.of(1990, Month.DECEMBER, 15, 15, 0).with(Month.JUNE);

        assertThat(inJune.getMonth()).isEqualTo(Month.JUNE);
    }

    @Test
    public void truncate() {
        LocalTime truncated = LocalTime.of(15, 12, 34).truncatedTo(ChronoUnit.HOURS);

        assertThat(truncated).isEqualTo(LocalTime.of(15, 0, 0));
    }

    @Test
    public void getTimeSpan() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime hourLater = now.plusHours(1);
        Duration span = Duration.between(now, hourLater);

        assertThat(span).isEqualTo(Duration.ofHours(1));
    }

    @Test
    public void formatAndParse() throws ParseException {
        LocalDate someDate = LocalDate.of(2016, 12, 7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = someDate.format(formatter);
        LocalDate parsedDate = LocalDate.parse(formattedDate, formatter);

        assertThat(formattedDate).isEqualTo("2016-12-07");
        assertThat(parsedDate).isEqualTo(someDate);
    }

    @Test
    public void daysInMonth() {
        int daysInMonth = YearMonth.of(1990, 2).lengthOfMonth();

        assertThat(daysInMonth).isEqualTo(28);
    }
}
