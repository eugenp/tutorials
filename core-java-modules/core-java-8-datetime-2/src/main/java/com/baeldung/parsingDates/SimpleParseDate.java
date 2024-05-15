package com.baeldung.parsingDates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SimpleParseDate {

    public static Date parseDate(String dateString, List<String> formatStrings) {
        for (String formatString : formatStrings) {
            try {
                return new SimpleDateFormat(formatString).parse(dateString);
            } catch (ParseException e) {
            }
        }
        return null;
    }
}
