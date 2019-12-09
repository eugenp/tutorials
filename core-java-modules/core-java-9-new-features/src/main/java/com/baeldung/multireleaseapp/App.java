package com.baeldung.multireleaseapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) throws Exception {
        String dateToCheck = args[0];
        boolean isLeapYear = DateHelper.checkIfLeapYear(dateToCheck);
        logger.info("Date given " + dateToCheck + " is leap year: " + isLeapYear);
    }

}
