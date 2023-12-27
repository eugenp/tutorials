package com.baeldung.localdatetoiso;

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
import org.joda.time.format.ISODateTimeFormat;

import org.apache.commons.lang3.time.FastDateFormat;

public class LocalDateToISO {
    public String formatUsingDateTimeFormatter(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        String formattedDate = localDate.atStartOfDay().atOffset(ZoneOffset.UTC).format(formatter);
        return formattedDate;
    }

    public String formatUsingSimpleDateFormat(LocalDate date) {
        Date utilDate = Date.from(date.atStartOfDay(ZoneOffset.UTC).toInstant());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        String formattedDate = dateFormat.format(utilDate);
        return formattedDate;
    }

    public String formatUsingJodaTime(org.joda.time.LocalDate localDate) {
        org.joda.time.format.DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
        return formatter.print(localDate.toDateTimeAtStartOfDay(DateTimeZone.UTC));
    }

     public String formatUsingApacheCommonsLang(LocalDate localDate) {
        Date date = Date.from(localDate.atStartOfDay().toInstant(ZoneOffset.UTC));
        String formattedDate = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", TimeZone.getTimeZone("UTC"))
            .format(date);
        return formattedDate;
    }
}
