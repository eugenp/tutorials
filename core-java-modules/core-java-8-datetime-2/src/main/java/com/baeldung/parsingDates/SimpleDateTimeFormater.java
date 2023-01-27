package com.baeldung.parsingDates;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class SimpleDateTimeFormater {

    public String parseDate(String date) {
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofPattern("[MM/dd/yyyy]" + "[dd-MM-yyyy]" + "[yyyy-MM-dd]"));

        DateTimeFormatter dateTimeFormatter = dateTimeFormatterBuilder.toFormatter();

        try {
            return dateTimeFormatter.parse(date).toString();
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
