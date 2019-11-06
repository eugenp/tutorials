package com.baeldung.date.comparison;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class LegacyDateComparisonUtils {

    public static boolean isSameDay(Date date, Date dateToCompare) {
        return DateUtils.isSameDay(date, dateToCompare);
    }

    public static boolean isSameHour(Date date, Date dateToCompare) {
        return DateUtils.truncatedEquals(date, dateToCompare, Calendar.HOUR);
    }
}
