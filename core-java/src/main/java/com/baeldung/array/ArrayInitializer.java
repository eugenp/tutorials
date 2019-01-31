package com.baeldung.array;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

import org.apache.commons.lang.ArrayUtils;

/**
 * 数组初始化
 * @author zn.wang
 */
public class ArrayInitializer {

    public static int[] initializeArrayInLoop() {
        int array[] = new int[5];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 2;
        }
        return array;
    }

    /**
     * 二纬数组
     * @return
     */
    public static int[][] initializeMultiDimensionalArrayInLoop() {
        int array[][] = new int[2][5];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                array[i][j] = j + 1;
            }
        }
        return array;
    }

    public static String[] initializeArrayAtTimeOfDeclarationMethod1() {
        String array[] = new String[] { "Toyota", "Mercedes", "BMW", "Volkswagen", "Skoda" };
        return array;
    }

    public static int[] initializeArrayAtTimeOfDeclarationMethod2() {
        int[] array = new int[] { 1, 2, 3, 4, 5 };
        return array;
    }

    public static int[] initializeArrayAtTimeOfDeclarationMethod3() {
        int array[] = { 1, 2, 3, 4, 5 };
        return array;
    }

    /**
     * @see java.util.Arrays#fill(long[] a, long val)
     * @return
     */
    public static long[] initializeArrayUsingArraysFill() {
        long array[] = new long[5];
        Arrays.fill(array, 30);
        return array;
    }

    /**
     * @see java.util.Arrays#fill(int[] a, int fromIndex, int toIndex, int val)
     * @return
     */
    public static int[] initializeArrayRangeUsingArraysFill() {
        int array[] = new int[5];
        Arrays.fill(array, 0, 3, -50);
        return array;
    }

    /**
     * @see java.util.Arrays#copyOf(int[] original, int newLength)
     * @return
     */
    public static int[] initializeArrayUsingArraysCopy() {
        int array[] = { 1, 2, 3, 4, 5 };
        int[] copy = Arrays.copyOf(array, 5);
        return copy;
    }

    /**
     * @see java.util.Arrays#copyOf(int[] original, int newLength)
     * @return
     */
    public static int[] initializeLargerArrayUsingArraysCopy() {
        int array[] = { 1, 2, 3, 4, 5 };
        int[] copy = Arrays.copyOf(array, 6);
        return copy;
    }

    /**
     * @see java.util.Arrays#setAll(int[], IntUnaryOperator)
     * @return
     */
    public static int[] initializeArrayUsingArraysSetAll() {
        int[] array = new int[20];

        Arrays.setAll(array, new IntUnaryOperator() {
            @Override
            public int applyAsInt(int p) {
                return p > 9 ? 0 : p;
            }
        });
        return array;
    }

    /**
     * @see org.apache.commons.lang.ArrayUtils#clone(char[] array)
     * @return
     */
    public static char[] initializeArrayUsingArraysUtilClone() {
        char[] array = new char[] { 'a', 'b', 'c' };
        return ArrayUtils.clone(array);
    }
}
