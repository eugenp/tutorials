package com.baeldung.parsingDates;

public class SimpleDateUtils {

    public Date parseDate(String date) {
        try {
            return DateUtils.parseDateStrictly(date,
                    new String[]{"yyyy/MM/dd", "dd/MM/yyyy", "yyyy-MM-dd"});
        } catch (ParseException ex) {
        }
        return null;
    }
}
