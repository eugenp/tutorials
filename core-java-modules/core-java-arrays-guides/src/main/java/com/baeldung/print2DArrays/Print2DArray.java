package com.baeldung.print2DArrays;

import java.util.*;

public class Print2DArray{

    public static void print2DNested(int[][] myArray){
        for (int i = 0; i < myArray.length; i++) { 
            for (int j = 0; j < myArray[i].length; j++) { 
                System.out.print(myArray[i][j] + " "); 
            }
            System.out.println(); 
        }
    }

    public static void print2DStream(int[][] myArray){ 
        Arrays.stream(myArray).flatMapToInt(Arrays::stream).forEach(num -> System.out.print(num + " "));
    }
  
    public static void print2DDeepToString(int[][] myArray){ 
        System.out.println(Arrays.deepToString(myArray));
    }
    
    public static void printArrayString(int[][] myArray) {
        for (int[] row : myArray) {
            System.out.println(Arrays.toString(row));
        }
     }
}
