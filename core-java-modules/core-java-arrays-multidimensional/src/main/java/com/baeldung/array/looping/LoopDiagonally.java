package com.baeldung.array.looping;

public class LoopDiagonally {


    public String loopDiagonally(String[][] twoDArray) {

        int length = twoDArray.length;
        int diagonalLines = (length + length) - 1;
        int itemsInDiagonal = 0;
        int midPoint = (diagonalLines / 2) + 1;
        StringBuilder output = new StringBuilder();

        for (int i = 1; i <= diagonalLines; i++) {

            StringBuilder items = new StringBuilder();
            int rowIndex;
            int columnIndex;

            if (i <= midPoint) {
                itemsInDiagonal++;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (i - j) - 1;
                    columnIndex = j;
                    items.append(twoDArray[rowIndex][columnIndex]);
                }
            } else {
                itemsInDiagonal--;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (length - 1) - j;
                    columnIndex = (i - length) + j;
                    items.append(twoDArray[rowIndex][columnIndex]);
                }
            }

            if (i != diagonalLines) {
                output.append(items).append(" ");
            } else {
                output.append(items);
            }
        }

        return output.toString();
    }
}
