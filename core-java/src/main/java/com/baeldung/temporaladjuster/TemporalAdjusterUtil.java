package com.baeldung.temporaladjuster;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TemporalAdjusterUtil {

    public static Calendar nextDayOfWeek(int dayOfWeek) {
        Calendar date = Calendar.getInstance();
        int difference = dayOfWeek - date.get(Calendar.DAY_OF_WEEK);
        if (!(difference > 0)) {
            difference += 7;
        }
        date.add(Calendar.DAY_OF_MONTH, difference);
        return date;
    }

    @SuppressWarnings("static-access")
    public static String getNextWorkingDay() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setCalendar(calendar);
        if (calendar.DAY_OF_WEEK == Calendar.FRIDAY)
            calendar.add(Calendar.DATE, 3);
        if (calendar.DAY_OF_WEEK == Calendar.SATURDAY)
            calendar.add(Calendar.DATE, 2);
        else
            calendar.add(Calendar.DATE, 1);
        return format.format(calendar.getTime());
    }
}
