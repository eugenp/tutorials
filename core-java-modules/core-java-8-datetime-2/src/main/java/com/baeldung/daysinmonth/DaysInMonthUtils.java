package com.baeldung.daysinmonth;

import java.time.YearMonth;
import java.util.Calendar;

public class DaysInMonthUtils {

    public int getDaysInMonthWithYearOfMonth(int month, int year) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }

    public int getDaysInMonthWithCalendar(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DATE);
    }

}
