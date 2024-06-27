package com.baeldung.nexthighestnumbersamedigit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NextHighestNumberUnitTest {

    @Test
    public void givenInput536479_whenUsingPermutation_thenOutputShouldBe536497() {
        int input = 536479;
        int result = NextHighestNumber.usingPermutation(input);
        assertEquals(536497, result);
    }

    @Test
    public void givenInput987654_whenUsingPermutation_thenOutputShouldBeMinusOne() {
        int input = 987654;
        int result = NextHighestNumber.usingPermutation(input);
        assertEquals(-1, result);
    }

    @Test
    public void givenInput444_whenUsingPermutation_thenOutputShouldBeMinusOne() {
        int input = 444;
        int result = NextHighestNumber.usingPermutation(input);
        assertEquals(-1, result);
    }

    @Test
    public void givenInput536479_whenUsingSorting_thenOutputShouldBe536497() {
        int input = 536479;
        int result = NextHighestNumber.usingSorting(input);
        assertEquals(536497, result);
    }

    @Test
    public void givenInput987654_whenUsingSorting_thenOutputShouldBeMinusOne() {
        int input = 987654;
        int result = NextHighestNumber.usingSorting(input);
        assertEquals(-1, result);
    }

    @Test
    public void givenInput444_whenUsingSorting_thenOutputShouldBeMinusOne() {
        int input = 444;
        int result = NextHighestNumber.usingSorting(input);
        assertEquals(-1, result);
    }

    @Test
    public void givenInput536479_whenUsingTwoPointer_thenOutputShouldBe536497() {
        int input = 536479;
        int result = NextHighestNumber.usingTwoPointer(input);
        assertEquals(536497, result);
    }

    @Test
    public void givenInput987654_whenUsingTwoPointer_thenOutputShouldBeMinusOne() {
        int input = 987654;
        int result = NextHighestNumber.usingTwoPointer(input);
        assertEquals(-1, result);
    }

    @Test
    public void givenInput444_whenUsingTwoPointer_thenOutputShouldBeMinusOne() {
        int input = 444;
        int result = NextHighestNumber.usingTwoPointer(input);
        assertEquals(-1, result);
    }
}
