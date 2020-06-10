package com.baeldung.globalexceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberFormat {

    private static Logger LOGGER = LoggerFactory.getLogger(NumberFormat.class);

    public static void main(String[] args) {

        String str1 = "100ABCD";

        try {
            int x = Integer.parseInt(str1); // Converting string with inappropriate format
            int y = Integer.valueOf(str1);
        } catch (NumberFormatException e) {
            LOGGER.error("NumberFormatException caught!");
        }

    }

}
