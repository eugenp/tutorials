package com.baeldung.numberofdigits;

import org.apache.log4j.Logger;

public class NumberOfDigitsDriver {
    private static NumberOfDigits numberOfDigits;

    private static Logger LOG = Logger.getLogger(NumberOfDigitsDriver.class);

    static {
        numberOfDigits = new NumberOfDigits();
    }

    public static void main(String[] args) {
        LOG.info("Testing all methods...");

        long length = numberOfDigits.stringBasedSolution(602);
        LOG.info("String Based Solution : " + length);

        length = numberOfDigits.logarithmicApproach(602);
        LOG.info("Logarithmic Approach : " + length);

        length = numberOfDigits.repeatedMultiplication(602);
        LOG.info("Repeated Multiplication : " + length);

        length = numberOfDigits.shiftOperators(602);
        LOG.info("Shift Operators : " + length);

        length = numberOfDigits.dividingWithPowersOf2(602);
        LOG.info("Dividing with Powers of 2 : " + length);

        length = numberOfDigits.divideAndConquer(602);
        LOG.info("Divide And Conquer : " + length);
    }
}