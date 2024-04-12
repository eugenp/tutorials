package com.baeldung.globalexceptionhandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseExceptionExample {

    private static Logger LOGGER = LoggerFactory.getLogger(ParseExceptionExample.class);

    public static void main(String[] args) {

        DateFormat format = new SimpleDateFormat("MM, dd, yyyy");

        try {
            format.parse("01, , 2010");
        } catch (ParseException e) {
            LOGGER.error("ParseException caught!");
        }
    }

}
