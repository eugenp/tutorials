package com.baeldung.array;

import java.util.Arrays;
import java.util.Scanner;

public class MultiDimensionalArray {

    int[][] shortHandFormInitialization() {
        int[][] multiDimensionalArray = { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 9 } };
        return multiDimensionalArray;
    }

    int[][] declarationAndThenInitialization() {
        int[][] multiDimensionalArray = new int[3][];
        multiDimensionalArray[0] = new int[] { 1, 2 };
        multiDimensionalArray[1] = new int[] { 3, 4, 5 };
        multiDimensionalArray[2] = new int[] { 6, 7, 8, 9 };
        return multiDimensionalArray;
    }

    int[][] declarationAndThenInitializationBaseOnIndex() {
        int[][] multiDimensionalArray = new int[3][];
        multiDimensionalArray[0] = new int[2];
        multiDimensionalArray[1] = new int[3];
        multiDimensionalArray[2] = new int[4];

        multiDimensionalArray[0][0] = 1;
        multiDimensionalArray[0][1] = 2;

        multiDimensionalArray[1][0] = 3;
        multiDimensionalArray[1][1] = 4;
        multiDimensionalArray[1][2] = 5;

        multiDimensionalArray[2][0] = 6;
        multiDimensionalArray[2][1] = 7;
        multiDimensionalArray[2][2] = 8;
        multiDimensionalArray[2][3] = 9;

        return multiDimensionalArray;
    }

    int[][] declarationAndThenInitializationUsingUserInputs() {
        int[][] multiDimensionalArray = new int[3][];
        multiDimensionalArray[0] = new int[2];
        multiDimensionalArray[1] = new int[3];
        multiDimensionalArray[2] = new int[4];

        initializeElements(multiDimensionalArray);
        return multiDimensionalArray;
    }

    int[][] declarationAndThenInitializationFirstAlternative() {
        int[][] multiDimensionalArray = new int[][] { new int[] { 1, 2 }, new int[] { 3, 4, 5 }, new int[] { 6, 7, 8, 9 } };

        return multiDimensionalArray;
    }

    int[][] declarationAndThenInitializationSecondAlternative() {
        int[][] multiDimensionalArray = { new int[] { 1, 2 }, new int[] { 3, 4, 5 }, new int[] { 6, 7, 8, 9 } };

        return multiDimensionalArray;
    }

    void initializeElements(int[][] multiDimensionalArray) {
        Scanner sc = new Scanner(System.in);
        for (int outer = 0; outer < multiDimensionalArray.length; outer++) {
            for (int inner = 0; inner < multiDimensionalArray[outer].length; inner++) {
                multiDimensionalArray[outer][inner] = sc.nextInt();
            }
        }
    }

    void initialize2DArray(int[][] multiDimensionalArray) {
        for (int[] array : multiDimensionalArray) {
            Arrays.fill(array, 7);
        }
    }

    void printElements(int[][] multiDimensionalArray) {
        for (int index = 0; index < multiDimensionalArray.length; index++) {
            System.out.println(Arrays.toString(multiDimensionalArray[index]));
        }
    }

    void printElementsUsingNestedForLoop(int[][] multiDimensionalArray) {
        for (int i = 0; i < multiDimensionalArray.length; i++) {
            for (int j = 0; j < multiDimensionalArray[i].length; j++) {
                System.out.print(multiDimensionalArray[i][j] + " ");
            }
            System.out.println();
        }
    }

    int[] getElementAtGivenIndex(int[][] multiDimensionalArray, int index) {
        return multiDimensionalArray[index];
    }

    int[] findLengthOfElements(int[][] multiDimensionalArray) {
        int[] arrayOfLengths = new int[multiDimensionalArray.length];
        for (int i = 0; i < multiDimensionalArray.length; i++) {
            arrayOfLengths[i] = multiDimensionalArray[i].length;
        }
        return arrayOfLengths;
    }

    Integer[] findLengthOfElements(Integer[][] multiDimensionalArray) {
        return Arrays.stream(multiDimensionalArray)
            .map(array -> array.length)
            .toArray(Integer[]::new);
    }

    int[][] copy2DArray(int[][] arrayOfArrays) {
        int[][] copied2DArray = new int[arrayOfArrays.length][];
        for (int i = 0; i < arrayOfArrays.length; i++) {
            int[] array = arrayOfArrays[i];
            copied2DArray[i] = Arrays.copyOf(array, array.length);
        }
        return copied2DArray;
    }

    Integer[][] copy2DArray(Integer[][] arrayOfArrays) {
        return Arrays.stream(arrayOfArrays)
            .map(array -> Arrays.copyOf(array, array.length))
            .toArray(Integer[][]::new);
    }
}
