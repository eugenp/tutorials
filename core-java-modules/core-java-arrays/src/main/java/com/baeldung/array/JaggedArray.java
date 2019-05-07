package com.baeldung.array;

import java.util.Arrays;
import java.util.Scanner;

public class JaggedArray {

    int[][] shortHandFormInitialization() {
        int[][] jaggedArr = { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 9 } };
        return jaggedArr;
    }

    int[][] declarationAndThenInitialization() {
        int[][] jaggedArr = new int[3][];
        jaggedArr[0] = new int[] { 1, 2 };
        jaggedArr[1] = new int[] { 3, 4, 5 };
        jaggedArr[2] = new int[] { 6, 7, 8, 9 };
        return jaggedArr;
    }

    int[][] declarationAndThenInitializationUsingUserInputs() {
        int[][] jaggedArr = new int[3][];
        jaggedArr[0] = new int[2];
        jaggedArr[1] = new int[3];
        jaggedArr[2] = new int[4];
        initializeElements(jaggedArr);
        return jaggedArr;
    }

    void initializeElements(int[][] jaggedArr) {
        Scanner sc = new Scanner(System.in);
        for (int outer = 0; outer < jaggedArr.length; outer++) {
            for (int inner = 0; inner < jaggedArr[outer].length; inner++) {
                jaggedArr[outer][inner] = sc.nextInt();
            }
        }
    }

    void printElements(int[][] jaggedArr) {
        for (int index = 0; index < jaggedArr.length; index++) {
            System.out.println(Arrays.toString(jaggedArr[index]));
        }
    }

    int[] getElementAtGivenIndex(int[][] jaggedArr, int index) {
        return jaggedArr[index];
    }

}
