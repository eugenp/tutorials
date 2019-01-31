package com.baeldung.array;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 二维数组的使用
 * @author zn.wang
 */
public class JaggedArray {

    public int[][] shortHandFormInitialization() {
        int[][] jaggedArr = {
                { 1, 2 },
                { 3, 4, 5 },
                { 6, 7, 8, 9 }
        };
        return jaggedArr;
    }

    public int[][] declarationAndThenInitialization() {
        int[][] jaggedArr = new int[3][];
        jaggedArr[0] = new int[] { 1, 2 };
        jaggedArr[1] = new int[] { 3, 4, 5 };
        jaggedArr[2] = new int[] { 6, 7, 8, 9 };
        return jaggedArr;
    }

    public int[][] declarationAndThenInitializationUsingUserInputs() {
        int[][] jaggedArr = new int[3][];
        jaggedArr[0] = new int[2];
        jaggedArr[1] = new int[3];
        jaggedArr[2] = new int[4];
        initializeElements(jaggedArr);
        return jaggedArr;
    }

    public void initializeElements(int[][] jaggedArr) {
        Scanner sc = new Scanner(System.in);
        for (int outer = 0; outer < jaggedArr.length; outer++) {
            for (int inner = 0; inner < jaggedArr[outer].length; inner++) {
                jaggedArr[outer][inner] = sc.nextInt();
            }
        }
    }

    /**
     * 打印二维数组
     * @param jaggedArr
     */
    public void printElements(int[][] jaggedArr) {
        for (int index = 0; index < jaggedArr.length; index++) {
            System.out.println(Arrays.toString(jaggedArr[index]));
        }
    }

    /**
     * 获取一维数组
     * @param jaggedArr
     * @param index
     * @return
     */
    public int[] getElementAtGivenIndex(int[][] jaggedArr, int index) {
        return jaggedArr[index];
    }

}
