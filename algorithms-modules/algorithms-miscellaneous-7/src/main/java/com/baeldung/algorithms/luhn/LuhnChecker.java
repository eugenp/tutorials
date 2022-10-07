package com.baeldung.algorithms.luhn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuhnChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(LuhnChecker.class);
    
    /*
     * Starting from the rightmost digit, we add all the digits together, performing
     * a special step for every second digit.
     * 
     * If the result is not divisible by 10, then the card number must not be valid.
     * 
     * We can form a number that passes the Luhn Check by subtracting the result of
     * the Luhn algorithm from 10.
     * 
     * This is how the final digit of a credit card is calculated.
     */
    public static boolean checkLuhn(String cardNumber) {
        int sum = 0;

        try {
            for (int i = cardNumber.length() - 1; i >= 0; i--) {
                int digit = Integer.parseInt(cardNumber.substring(i, i + 1));

                if ((cardNumber.length() - i) % 2 == 0) {
                    digit = doubleAndSumDigits(digit);
                }

                sum += digit;
            }
            
            LOGGER.info("Luhn Algorithm sum of digits is " + sum);
            
        } catch (NumberFormatException e) {
            LOGGER.error("NumberFormatException - Card number probably contained some non-numeric characters, returning false");
            return false;
        } catch (NullPointerException e) {
            LOGGER.error("Null pointer - Card number was probably null, returning false");
            return false;
        }
        
        boolean result = sum % 10 == 0;
        
        LOGGER.info("Luhn check result (sum divisible by 10): " + result);
        
        return result;
    }

    /*
     * We apply this method to every second number from the right of the card
     * number. First, we double the digit, then we sum the digits.
     * 
     * Note: subtracting 9 is equivalent to doubling and summing digits (when
     * starting with a single digit) 0-4 -> produce single digit when doubled
     * 5*2 = 10 -> 1+0 = 1 = 10-9 
     * 6*2 = 12 -> 1+3 = 3 = 12-9 
     * 7*2 = 14 -> 1+5 = 5 = 14-9
     * 8*2 = 16 -> 1+7 = 7 = 16-9 
     * 9*2 = 18 -> 1+9 = 9 = 18-9
     */
    public static int doubleAndSumDigits(int digit) {
        int ret = digit * 2;

        if (ret > 9) {
            ret -= 9;
        }

        return ret;
    }
}
