package com.baeldung.array;

import java.util.Arrays;

/**
 * @author zn.wang
 */
public class SumAndAverageInArray {

    /**
     * for循环求和
     * @param array
     * @return
     */
    public static int findSumWithoutUsingStream(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    /**
     * 使用{@link java.util.Arrays#stream(Object[] java.util) 求和}
     * @param array
     * @return
     */
    public static int findSumUsingStream(int[] array) {
        return Arrays.stream(array).sum();
    }

    /**
     * 求和
     * @param array
     * @return
     */
    public static int findSumUsingStream(Integer[] array) {
        return Arrays.stream(array).mapToInt(Integer::intValue).sum();
    }

    /**
     * 求平均值
     * @param array
     * @return
     */
    public static double findAverageWithoutUsingStream(int[] array) {
        int sum = findSumWithoutUsingStream(array);
        return (double) sum / array.length;
    }

    /**
     * 求平均值
     * @param array
     * @return
     */
    public static double findAverageUsingStream(int[] array) {
        return Arrays.stream(array).average().orElse(Double.NaN);
    }
}
