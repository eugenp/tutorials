package com.baeldung.parsingDates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class SimpleParseDate {

    public static String parseDate(String dateString, List<String> formatStrings) {
        for (String formatString : formatStrings) {
            try {
                return new SimpleDateFormat(formatString).parse(dateString).toString();
            } catch (ParseException e) {
            }
        }
        return null;
    }
}
