package com.baeldung.array;

import org.junit.Assert;
import org.junit.Test;

public class FindElementInArrayTest {
    @Test
    public void whenGivenAnIntArrayFindAnElement() {
        int[] array = {1, 3, 4, 8, 19, 20};
        int element = 19;
        boolean expectedResult = true;
        boolean actualResult = false;

        for (int index = 0; index < array.length; index++) {
            if (element == array[index]) {
                actualResult = true;
                break;
            }
        }
        Assert.assertEquals(expectedResult, actualResult);

        element = 78;
        expectedResult = false;
        actualResult = false;

        for (int index = 0; index < array.length; index++) {
            if (element == array[index]) {
                actualResult = true;
                break;
            }
        }
        Assert.assertEquals(expectedResult, actualResult);

    }
}
