package com.baeldung.multireleaseapp;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateHelper {

    private static final Logger logger = LoggerFactory.getLogger(DateHelper.class);

    public static boolean checkIfLeapYear(String dateStr) throws Exception {
        logger.info("Checking for leap year using Java 9 Date Api");
        return LocalDate.parse(dateStr)
            .isLeapYear();
    }

}
