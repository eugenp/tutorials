package com.baeldung.parsingDates;

import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

public class SimpleDateUtils {

    public static Date parseDate(String date) {
        try {
            return DateUtils.parseDateStrictly(date,
                    new String[]{"yyyy/MM/dd", "dd/MM/yyyy", "yyyy-MM-dd"});
        } catch (ParseException ex) {
            return null;
        }
    }

}
