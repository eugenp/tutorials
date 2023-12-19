package com.baeldung.monthintervalbetweentwodates;

import java.util.Calendar;
import java.util.Date;

public class MonthInterval {

    public int monthsBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Both startDate and endDate must be provided");
        }
        
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        int startDateTotalMonths = 12 * startCalendar.get(Calendar.YEAR) + startCalendar.get(Calendar.MONTH);
        
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        int endDateTotalMonths = 12 * endCalendar.get(Calendar.YEAR) + endCalendar.get(Calendar.MONTH);
        
        return endDateTotalMonths - startDateTotalMonths;
    }

    public int monthsBetweenWithDayValue(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Both startDate and endDate must be provided");
        }
        
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        
        int startDateDayOfMonth = startCalendar.get(Calendar.DAY_OF_MONTH);
        int startDateTotalMonths = 12 * startCalendar.get(Calendar.YEAR) + startCalendar.get(Calendar.MONTH);
        
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        
        int endDateDayOfMonth = endCalendar.get(Calendar.DAY_OF_MONTH);
        int endDateTotalMonths = 12 * endCalendar.get(Calendar.YEAR) + endCalendar.get(Calendar.MONTH);

        return (startDateDayOfMonth > endDateDayOfMonth) ? (endDateTotalMonths - startDateTotalMonths) - 1 : (endDateTotalMonths - startDateTotalMonths);
    }

}
