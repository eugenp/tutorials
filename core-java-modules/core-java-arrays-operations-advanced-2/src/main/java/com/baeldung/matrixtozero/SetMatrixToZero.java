package com.baeldung.matrixtozero;

import java.util.*;

public class SetMatrixToZero{
    static void setZeroesByNaiveApproach(int[][] matrix) {
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
    
    static void setZeroesByTimeOptimizedApproach(int[][] matrix) {
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

    static boolean hasZeroInFirstRow(int[][] matrix, int cols) {
        for (int j = 0; j < cols; j++) {
            if (matrix[0][j] == 0) {
                return true;
            }
        }
        return false;
    }

    static boolean hasZeroInFirstCol(int[][] matrix, int rows) {
        for (int i = 0; i < rows; i++) {
            if (matrix[i][0] == 0) {
                return true;
            }
        }
        return false;
    }

    static void markZeroesInMatrix(int[][] matrix, int rows, int cols) {
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
    }

    static void setZeroesInRows(int[][] matrix, int rows, int cols) {
        for (int i = 1; i < rows; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < cols; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    static void setZeroesInCols(int[][] matrix, int rows, int cols) {
        for (int j = 1; j < cols; j++) {
            if (matrix[0][j] == 0) {
                for (int i = 1; i < rows; i++) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    static void setZeroesInFirstRow(int[][] matrix, int cols) {
        for (int j = 0; j < cols; j++) {
            matrix[0][j] = 0;
        }
    }

    static void setZeroesInFirstCol(int[][] matrix, int rows) {
        for (int i = 0; i < rows; i++) {
            matrix[i][0] = 0;
        }
    }

    static void setZeroesByOptimalApproach(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        boolean firstRowZero = hasZeroInFirstRow(matrix, cols);
        boolean firstColZero = hasZeroInFirstCol(matrix, rows);
        
        markZeroesInMatrix(matrix, rows, cols);
        
        setZeroesInRows(matrix, rows, cols);
        setZeroesInCols(matrix, rows, cols);
        
        if (firstRowZero) {
            setZeroesInFirstRow(matrix, cols);
        }
        if (firstColZero) {
            setZeroesInFirstCol(matrix, rows);
        }
    }

}
