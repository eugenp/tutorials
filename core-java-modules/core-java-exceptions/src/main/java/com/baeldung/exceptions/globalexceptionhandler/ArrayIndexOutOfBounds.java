package com.baeldung.exceptions.globalexceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArrayIndexOutOfBounds {

    private static Logger LOGGER = LoggerFactory.getLogger(ArrayIndexOutOfBounds.class);

    public static void main(String[] args) {

        int[] nums = new int[] { 1, 2, 3 };

        try {
            int numFromNegativeIndex = nums[-1]; // Trying to access at negative index
            int numFromGreaterIndex = nums[4]; // Trying to access at greater index
            int numFromLengthIndex = nums[3]; // Trying to access at index equal to size of the array
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.error("ArrayIndexOutOfBoundsException caught");
        }

    }

}
