package com.baeldung.junit5.bean;

/**
 * Bean that contains utility methods to work with numbers.
 * 
 * @author Donato Rimenti
 *
 */
public class NumbersBean {

    /**
     * Returns true if a number is even, false otherwise.
     * 
     * @param number
     *            the number to check
     * @return true if the argument is even, false otherwise
     */
    public boolean isNumberEven(int number) {
        return number % 2 == 0;
    }

}
