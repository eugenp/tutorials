package com.baeldung.array;

/**
 * 数组中找第2大元素
 * @author zn.wang
 */
public class Find2ndLargestInArray {

    /**
     * @param array
     * @return
     */
    public static int find2ndLargestElement(int[] array) {
        int maxElement = array[0];
        int secondLargestElement = -1;

        for (int index = 0; index < array.length; index++) {
            if (maxElement <= array[index]) {
                secondLargestElement = maxElement;
                maxElement = array[index];
            }
            else if (secondLargestElement < array[index]) {
                secondLargestElement = array[index];
            }
        }
        return secondLargestElement;
    }

}
