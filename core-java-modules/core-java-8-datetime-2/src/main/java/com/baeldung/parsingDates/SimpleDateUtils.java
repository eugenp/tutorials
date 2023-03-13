package com.baeldung.parsingDates;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.commons.lang3.time.DateUtils;

public class SimpleDateUtils {

    public static String parseDate(String date) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return df.format(DateUtils.parseDateStrictly(date,
                    new String[]{"yyyy/MM/dd", "dd/MM/yyyy", "yyyy-MM-dd"}));
        } catch (ParseException ex) {
            return null;
        }
    }

}
