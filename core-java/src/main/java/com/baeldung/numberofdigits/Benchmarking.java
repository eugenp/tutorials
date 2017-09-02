package com.baeldung.numberofdigits;

import static com.baeldung.designpatterns.util.LogerUtil.LOG;;

public class Benchmarking {;

    private static final int LOWER_BOUND = 1;
    private static final int UPPER_BOUND = 999999999;

    
    public static void main(String[] args) {
        LOG.info("Testing all methods...");
        
        long length = test_stringBasedSolution();
        LOG.info("String Based Solution : " + length);
        
        length = test_logarithmicApproach();
        LOG.info("Logarithmic Approach : " + length);
        
        length = test_repeatedMultiplication();
        LOG.info("Repeated Multiplication : " + length);
        
        length = test_shiftOperators();
        LOG.info("Shift Operators : " + length);
        
        length = test_dividingWithPowersOf2();
        LOG.info("Dividing with Powers of 2 : " + length);
        
        length = test_divideAndConquer();
        LOG.info("Divide And Conquer : " + length);

    }

    private static long test_stringBasedSolution() {
        
        long startTime, stopTime, elapsedTime;
        startTime = System.currentTimeMillis();

        int total = 0;
        for (int i = LOWER_BOUND; i <= UPPER_BOUND; i++) {
            total += NumberOfDigits.stringBasedSolution(i);
        }

        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        
        return elapsedTime;
    }

    private static long test_logarithmicApproach() {
        
        long startTime, stopTime, elapsedTime;
        startTime = System.currentTimeMillis();

        int total = 0;
        for (int i = LOWER_BOUND; i <= UPPER_BOUND; i++) {
            total += NumberOfDigits.logarithmicApproach(i);
        }

        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        
        return elapsedTime;
    }

    private static long test_repeatedMultiplication() {
        
        long startTime, stopTime, elapsedTime;
        startTime = System.currentTimeMillis();

        int total = 0;
        for (int i = LOWER_BOUND; i <= UPPER_BOUND; i++) {
            total += NumberOfDigits.repeatedMultiplication(i);
        }

        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        
        return elapsedTime;
    }

    private static long test_shiftOperators() {
        
        long startTime, stopTime, elapsedTime;
        startTime = System.currentTimeMillis();

        int total = 0;
        for (int i = LOWER_BOUND; i <= UPPER_BOUND; i++) {
            total += NumberOfDigits.shiftOperators(i);
        }

        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        
        return elapsedTime;
    }

    private static long test_dividingWithPowersOf2() {
        
        long startTime, stopTime, elapsedTime;
        startTime = System.currentTimeMillis();

        int total = 0;
        for (int i = LOWER_BOUND; i <= UPPER_BOUND; i++) {
            total += NumberOfDigits.dividingWithPowersOf2(i);
        }

        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        
        return elapsedTime;
    }

    private static long test_divideAndConquer() {
        
        long startTime, stopTime, elapsedTime;
        startTime = System.currentTimeMillis();

        int total = 0;
        for (int i = LOWER_BOUND; i <= UPPER_BOUND; i++) {
            total += NumberOfDigits.divideAndConquer(i);
        }

        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        
        return elapsedTime;
    }
    
    
}
