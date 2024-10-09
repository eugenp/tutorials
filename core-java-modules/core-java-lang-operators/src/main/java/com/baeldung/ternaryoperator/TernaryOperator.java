package com.baeldung.ternaryoperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TernaryOperator {

    private final static Logger LOGGER = LoggerFactory.getLogger(TernaryOperator.class);

    public static String checkNumber(int num) {
        return num > 10 ? "Number is greater than 10" : "Number is less than or equal to 10";
    }

    public static void checkNumberWithoutReturnStatement(int num) {
        if (num > 10) {
            LOGGER.info("Number is greater than 10");
        } else {
            LOGGER.info("Number is less than or equal to 10");
        }
    }

}
