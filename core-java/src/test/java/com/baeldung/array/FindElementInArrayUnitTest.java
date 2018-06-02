package com.baeldung.array;

import org.junit.Assert;
import org.junit.Test;

public class FindElementInArrayUnitTest {
    @Test
    public void givenAnIntArray_whenNotUsingStream_thenFindAnElement() {
        int[] array = { 1, 3, 4, 8, 19, 20 };
        int element = 19;
        boolean expectedResult = true;
        boolean actualResult = FindElementInArray.findGivenElementInArrayWithoutUsingStream(array, element);
        Assert.assertEquals(expectedResult, actualResult);

        element = 78;
        expectedResult = false;
        actualResult = FindElementInArray.findGivenElementInArrayWithoutUsingStream(array, element);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenAnIntArray_whenUsingStream_thenFindAnElement() {
        int[] array = { 15, 16, 12, 18 };
        int element = 16;
        boolean expectedResult = true;
        boolean actualResult = FindElementInArray.findGivenElementInArrayUsingStream(array, element);
        Assert.assertEquals(expectedResult, actualResult);

        element = 20;
        expectedResult = false;
        actualResult = FindElementInArray.findGivenElementInArrayUsingStream(array, element);
        Assert.assertEquals(expectedResult, actualResult);
    }

}
