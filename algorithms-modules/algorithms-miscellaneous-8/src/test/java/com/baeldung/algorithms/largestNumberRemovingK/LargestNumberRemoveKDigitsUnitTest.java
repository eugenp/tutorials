package com.baeldung.algorithms.largestNumberRemovingK;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LargestNumberRemoveKDigitsUnitTest {

    @Test
    public void givenNumber_UsingArithmeticRemoveKDigits_thenReturnLargestNumber(){
        Assertions.assertEquals(LargestNumberRemoveKDigits.findLargestNumberUsingArithmetic(9461, 1), 961);
        Assertions.assertEquals(LargestNumberRemoveKDigits.findLargestNumberUsingArithmetic(463, 2), 6);
        Assertions.assertEquals(LargestNumberRemoveKDigits.findLargestNumberUsingArithmetic(98625410, 6), 98);
        Assertions.assertEquals(LargestNumberRemoveKDigits.findLargestNumberUsingArithmetic(20, 2), 0);
        Assertions.assertEquals(LargestNumberRemoveKDigits.findLargestNumberUsingArithmetic(98989, 4), 9);
    }

    @Test
    public void givenNumber_UsingStackRemoveKDigits_thenReturnLargestNumber(){
        Assertions.assertEquals(LargestNumberRemoveKDigits.findLargestNumberUsingStack(9461, 1), 961);
        Assertions.assertEquals(LargestNumberRemoveKDigits.findLargestNumberUsingStack(463, 2), 6);
        Assertions.assertEquals(LargestNumberRemoveKDigits.findLargestNumberUsingStack(98625410, 6), 98);
        Assertions.assertEquals(LargestNumberRemoveKDigits.findLargestNumberUsingStack(20, 2), 0);
        Assertions.assertEquals(LargestNumberRemoveKDigits.findLargestNumberUsingStack(98989, 4), 9);
    }
}
