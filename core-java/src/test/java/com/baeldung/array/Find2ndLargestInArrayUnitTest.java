package com.baeldung.array;

import org.junit.Assert;
import org.junit.Test;


/**
 * 数组中找第2大元素
 */
public class Find2ndLargestInArrayUnitTest {

    /**
     * 如果数组中有多个最大的数，那么第2大数，会打印出最大的数。
     */
    @Test
    public void givenAnIntArray_thenFind2ndLargestElement() {
        int[] array = { 1, 3, 24, 16, 87, 20 };
        int expected2ndLargest = 24;
        int actualSecondLargestElement = Find2ndLargestInArray.find2ndLargestElement(array);

        Assert.assertEquals(expected2ndLargest, actualSecondLargestElement);

        int [] arrayTwo = {1 , 5 , 2 , 3 , 5 ,  4} ;
        System.out.println(Find2ndLargestInArray.find2ndLargestElement(arrayTwo));
    }
}
