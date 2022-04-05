package com.baeldung.rethrow;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RethrowSameExceptionDemo {

    private final static Logger LOGGER = Logger.getLogger(RethrowDifferentExceptionDemo.class.getName());

    public static void main(String[] args) throws Exception {
        String name = null;

        try {

            // Below line will throw NullPointerException
            if (name.equals("Joe")) {
                // Do blah blah..
            }

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception occurred due to invalid name");
            throw e;
        }

    }

}
