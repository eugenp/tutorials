package com.baeldung.multireleaseapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateHelper {

    public static boolean checkIfLeapYear(String dateStr) throws Exception {
        System.out.println("Checking for leap year using Java 1 calendar API ");
        boolean isLeapYear = false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
        int year = cal.get(Calendar.YEAR);
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                isLeapYear = (year % 400 == 0) ? true : false;
            } else {
                isLeapYear = true;
            }
        }
        return isLeapYear;
    }

}
