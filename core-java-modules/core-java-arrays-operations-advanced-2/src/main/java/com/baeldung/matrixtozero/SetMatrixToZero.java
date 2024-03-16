package com.baeldung.matrixtozero;

import java.util.*;

public class SetMatrixToZero{
    static void setZeroesBruteForce(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int [][] result = new int[row][col];

        for(int i = 0; i<row; i++){
            for(int j = 0; j < col; j++){
                result[i][j] = matrix[i][j];
            }
        }
        
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(matrix[i][j] == 0){
                    for(int k = 0; k < col; k++){
                        result[i][k] = 0;
                    }
                    for(int k = 0; k < row; k++){
                        result[k][j] = 0;
                    }
                }
            }
        }

        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                matrix[i][j] = result[i][j];
            }
        }
    }
    
    static void setZeroesExtraSpace(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        Set<Integer> rowSet = new HashSet<>();
        Set<Integer> colSet = new HashSet<>();

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if (matrix[i][j] == 0){
                    rowSet.add(i);
                    colSet.add(j);
                }
            }
        }
        
        for(int row: rowSet){
            for(int j = 0; j < cols; j++){
                matrix[row][j] = 0;
            }
        }
        
        for(int col: colSet) {
            for(int i = 0; i < rows; i++){
                matrix[i][col] = 0;
            }
        }
    }
    
    static void setZeroesOptimized(int[][] matrix){
        int rows = matrix.length;
        int cols = matrix[0].length;

        boolean firstRowZero = false;
        boolean firstColZero = false;

        for(int j = 0; j < cols; j++){
            if (matrix[0][j] == 0) {
                firstRowZero = true;
                break;
            }
        }

        for(int i = 0; i < rows; i++){
            if (matrix[i][0] == 0) {
                firstColZero = true;
                break;
            }
        }
        
        for(int i = 1; i < rows; i++){
            for(int j = 1; j < cols; j++){
                if (matrix[i][j] == 0){
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        
        for(int i = 1; i < rows; i++){
            if(matrix[i][0] == 0){
                for(int j = 1; j < cols; j++){
                    matrix[i][j] = 0;
                }
            }
        }
        
        for(int j = 1; j < cols; j++){
            if(matrix[0][j] == 0) {
                for(int i = 1; i < rows; i++){
                    matrix[i][j] = 0;
                }
            }
        }
        
        if(firstRowZero){
            for(int j = 0; j < cols; j++){
                matrix[0][j] = 0;
            }
        }
        
        if(firstColZero){
            for(int i = 0; i < rows; i++){
                matrix[i][0] = 0;
            }
        }
    }
}
