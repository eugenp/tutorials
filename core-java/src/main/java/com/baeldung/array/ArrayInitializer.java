package com.baeldung.array;

import java.util.Arrays;

public class ArrayInitializer {

    public static int[] initializeArrayInLoop() {
        int array[] = new int[5];
        for (int i = 0; i < array.length; i++)
            array[i] = i + 2;
        return array;
    }

    public static int[][] initializeMultiDimensionalArrayInLoop() {
        int array[][] = new int[2][5];
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 5; j++)
                array[i][j] = j + 1;
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

    public static long[] initializeArrayUsingArraysFill() {
        long array[] = new long[5];
        Arrays.fill(array, 30);
        return array;
    }

    public static int[] initializeArrayRangeUsingArraysFill() {
        int array[] = new int[5];
        Arrays.fill(array, 0, 3, -50);
        return array;
    }
}
