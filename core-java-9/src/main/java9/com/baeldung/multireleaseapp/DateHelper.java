package com.baeldung.multireleaseapp;

import java.time.LocalDate;

public class DateHelper {

    public static boolean checkIfLeapYear(String dateStr) throws Exception {
        System.out.println("Checking for leap year using Java 9 Date Api");
        return LocalDate.parse(dateStr)
            .isLeapYear();
    }

}
