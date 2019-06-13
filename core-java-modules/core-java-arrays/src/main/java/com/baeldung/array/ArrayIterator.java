package com.baeldung.array;

public class ArrayIterator {

	int[][] evenArray = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
	int[][] unevenArray = { { 1, 2 }, { 6 }, { 11, 12, 13, 14, 15 }, { 16, 17, 18, 19, 20 }, { 21, 22, 23 } };

	public static void printAll(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public static void printColumn(int[][] arr, int index) {
		for (int i = 0; i < arr.length; i++) {
			if (index < arr[i].length) {
				System.out.print(arr[i][index] + "\t");
			}
		}
	}

	public static void printDiagonally(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			if (i < array[i].length) {
				System.out.print(array[i][i] + "\t");
			} else {
				System.out.print("-\t");
			}
		}
	}

	public static void printRow(int[][] arr, int index) {
		if (index < 0 || index >= arr.length) {
			return;
		}

		int[] row = arr[index];
		for (int i = 0; i < row.length; i++) {
			System.out.print(row[i] + "\t");
		}
	}

	public static void printWithOffset(int[][] arr, int row, int col, int rowOffset, int colOffset) {
		for (; row >= 0 && row < arr.length; row += rowOffset) {
			if (col >= 0 && col < arr[row].length) {
				System.out.print(arr[row][col] + "\t");
			} else if (rowOffset == 0 && (colOffset > 0 || col < 0)) {
				break;
			} else {
				System.out.print("-\t");
			}

			if (rowOffset == 0 && colOffset == 0) {
				break;
			}
			col += colOffset;
		}
	}
}