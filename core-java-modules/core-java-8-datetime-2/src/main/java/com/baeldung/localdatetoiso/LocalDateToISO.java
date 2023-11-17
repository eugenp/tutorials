package com.baeldung.localDateToISO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

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
}
