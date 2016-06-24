package com.baeldung.date_migration;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class NewApi {
    public void currentTime() {
        ZonedDateTime now = ZonedDateTime.now();
    }

    public void specificTime() {
        LocalDate birthDay = LocalDate.of(1990, Month.DECEMBER, 15);
    }

    public void extractMonth() {
        Month month = LocalDateTime.now().getMonth();
    }

    public void subtractTime() {
        LocalDateTime fiveHoursBefore = LocalDateTime.now().minusHours(5);
    }

    public void alterField() {
        LocalDateTime inJune = LocalDateTime.now().withMonth(Month.JUNE.getValue());
    }

    public void truncate() {
        LocalTime truncated = LocalTime.now().truncatedTo(ChronoUnit.HOURS);
    }

    public void convertTimeZone() {
        ZonedDateTime centralEastern = LocalDateTime.now().atZone(ZoneId.of("CET"));
    }

    public void getTimeSpan() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime hourLater = LocalDateTime.now().plusHours(1);
        Duration span = Duration.between(now, hourLater);
    }

    public void formatAndParse() throws ParseException {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = now.format(formatter);
        LocalDate parsedDate = LocalDate.parse(formattedDate, formatter);
    }

    public void daysInMonth() {
        int daysInMonth = YearMonth.of(1990, 2).lengthOfMonth();
    }

}
