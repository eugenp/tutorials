package com.baeldung.array.reversearray;

public class ReverseArrayElements {

    public static void reverseRows(int[][] array) {
        for (int[] cols : array) {
            int numCols = cols.length;

            for (int col = 0; col < numCols / 2; col++) {
                swap(cols, col, numCols - 1 - col);
            }
        }
    }

    public static void reverseColumns(int[][] array) {
        for (int col = 0; col < array[0].length; col++) {
            int numRows = array.length;

            for (int row = 0; row < numRows / 2; row++) {
                swap(array[row], numRows - 1 - row, row);
            }
        }
    }

    private static void swap(int[] array, int current, int target) {
        int toSwap = array[current];
        array[current] = array[target];
        array[target] = toSwap;
    }
}

// 4 1 2 3    0,0 0,1 0,2 0,3
// 6 7 8 1    1,0 1,1 1,2 1,3
// 2 3 5 4    2,0 2,1 2,2 2,3
