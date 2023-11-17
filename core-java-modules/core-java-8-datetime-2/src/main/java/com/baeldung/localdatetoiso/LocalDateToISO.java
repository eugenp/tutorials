package com.baeldung.localDateToISO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

public class LocalDateToISO {
    public String formatUsingDateTimeFormatter(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
        String formattedDate = localDate.atStartOfDay().atOffset(ZoneOffset.UTC).format(formatter);
        return formattedDate;
    }

    public String formatUsingSimpleDateFormat(LocalDate date) {
        Date utilDate = Date.from(date.atStartOfDay(ZoneOffset.UTC).toInstant());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        String formattedDate = dateFormat.format(utilDate);
        return formattedDate;
    }

    public String formatUsingJodaTime(org.joda.time.LocalDate localDate) {
        DateTime dateTime = localDate.toDateTimeAtStartOfDay(DateTimeZone.UTC);
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String formattedDate = formatter.print(localDate);
        return formattedDate;
    }
}
