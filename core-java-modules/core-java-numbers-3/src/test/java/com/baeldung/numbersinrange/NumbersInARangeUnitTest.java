package com.baeldung.numbersinrange;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class NumbersInARangeUnitTest {

    @Test
    public void givenTheRange1To10_andUsingForLoop_thenExpectCorrectResult() {
        NumbersInARange numbersInARange = new NumbersInARange();
        List<Integer> numbers = numbersInARange.getNumbersInRange(1, 10);
        
        assertEquals(Arrays.asList(1,2,3,4,5,6,7,8,9), numbers);
    }
    
    @Test
    public void givenTheRange1To10_andUsingIntStreamRange_thenExpectCorrectResult() {
        NumbersInARange numbersInARange = new NumbersInARange();
        List<Integer> numbers = numbersInARange.getNumbersUsingIntStreamRange(1, 10);
        
        assertEquals(Arrays.asList(1,2,3,4,5,6,7,8,9), numbers);
    }
    
    @Test
    public void givenTheRange1To10_andUsingIntStreamRangeClosed_thenExpectCorrectResult() {
        NumbersInARange numbersInARange = new NumbersInARange();
        List<Integer> numbers = numbersInARange.getNumbersUsingIntStreamRangeClosed(1, 10);
        
        assertEquals(Arrays.asList(1,2,3,4,5,6,7,8,9,10), numbers);
    }
    
    @Test
    public void givenTheRange1To10_andUsingIntStreamIterate_thenExpectCorrectResult() {
        NumbersInARange numbersInARange = new NumbersInARange();
        List<Integer> numbers = numbersInARange.getNumbersUsingIntStreamIterate(1, 10);
        
        assertEquals(Arrays.asList(1,2,3,4,5,6,7,8,9,10), numbers);
    }
}
