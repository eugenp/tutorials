package com.baeldung.uniquedigitcounter;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UniqueDigitCounterUnitTest {

    @Test 
    public void givenNumber_whenUsingBruteForce_thenCountNumbersWithUniqueDigits() { 
        assertEquals(91, UniqueDigitCounter.bruteForce(2)); 
    }

    @Test 
    public void givenNumber_whenUsingCombinatorial_thenCountNumbersWithUniqueDigits() { 
        assertEquals(91, UniqueDigitCounter.bruteForce(2)); 
    }

    @Test 
    public void givenNumber_whenUsingDp_thenCountNumbersWithUniqueDigits() { 
        assertEquals(91, UniqueDigitCounter.bruteForce(2)); 
    }
}
