package com.baeldung.parsingDates;

import java.util.Arrays;
import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class SimpleDateTimeFormat {

    public static LocalDate parseDate(String date) {
        List<String> patternList = Arrays.asList("MM/dd/yyyy", "dd.MM.yyyy", "yyyy-MM-dd");
        for (String pattern : patternList) {
            try {
                return DateTimeFormat.forPattern(pattern).parseLocalDate(date);
            } catch (IllegalArgumentException e) {
            }
        }
        return null;
    }
}
