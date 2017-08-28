package com.baeldung.numberofdigits;

import static com.baeldung.designpatterns.util.LogerUtil.LOG;

public class NumberOfDigitsDriver {
    public static void main(String[] args) {
        LOG.info("Testing all methods...");
        
        long length = NumberOfDigits.stringBasedSolution(602);
        LOG.info("String Based Solution : " + length);
        
        length = NumberOfDigits.logarithmicApproach(602);
        LOG.info("Logarithmic Approach : " + length);
        
        length = NumberOfDigits.repeatedMultiplication(602);
        LOG.info("Repeated Multiplication : " + length);
        
        length = NumberOfDigits.shiftOperators(602);
        LOG.info("Shift Operators : " + length);
        
        length = NumberOfDigits.dividingWithPowersOf2(602);
        LOG.info("Dividing with Powers of 2 : " + length);
        
        length = NumberOfDigits.divideAndConquer(602);
        LOG.info("Divide And Conquer : " + length);
    }
}
