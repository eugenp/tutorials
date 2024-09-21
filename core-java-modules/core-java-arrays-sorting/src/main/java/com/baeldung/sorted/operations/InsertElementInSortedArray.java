package com.baeldung.sorted.operations;

import java.util.Arrays;

public class InsertElementInSortedArray {
   
    public static int[] insertInSortedArr(int[] arr,int numToInsert) {
        int index = Arrays.binarySearch(arr, numToInsert);
        
        if (index < 0) {
            index = -(index + 1);
        }
        
        int[] newArr = new int[arr.length + 1];
        
        System.arraycopy(arr, 0, newArr, 0, index);
        
        newArr[index] = numToInsert;
        
        System.arraycopy(arr, index, newArr, index + 1, arr.length - index);
        
        arr = newArr;
        return newArr;
    }
}
