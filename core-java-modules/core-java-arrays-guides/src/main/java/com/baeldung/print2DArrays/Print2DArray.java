package com.baeldung.print2DArrays;

import java.util.*;

public class Print2DArray{

    public static String print2DNested(int[][] myArray){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < myArray.length; i++) { 
            for (int j = 0; j < myArray[i].length; j++) { 
                result.append(myArray[i][j]).append(" "); 
            }
            result.append("\n"); 
        }
        return result.toString();
    }

    public static String print2DStream(int[][] myArray){ 
        StringBuilder result = new StringBuilder();
        Arrays.stream(myArray).flatMapToInt(Arrays::stream).forEach(num -> result.append(num).append(" "));
        return result.toString();
    }
  
    public static String print2DDeepToString(int[][] myArray){ 
        StringBuilder result = new StringBuilder(); 
        result.append(Arrays.deepToString(myArray)).append("\n");
        return result.toString();
    }
    
    public static String printArrayString(int[][] myArray) {
        StringBuilder result = new StringBuilder(); 
        for (int[] row : myArray) {
            result.append(Arrays.toString(row)).append("\n");
        }
        return result.toString();
     }
}
