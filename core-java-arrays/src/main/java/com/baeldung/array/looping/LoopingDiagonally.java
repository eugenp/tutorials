package com.baeldung.array.looping;


public class LoopDiagonally {

    public void loopDiagonally(String[][] twoDArray, StringBuilder builder) {

        int length = twoDArray.length;
        int diagonalLines = (length + length) - 1;
        int itemsInDiagonal = 0;
        int midPoint = (diagonalLines / 2) + 1;

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
                builder.append(items).append(" ");
            } else {
                builder.append(items);
            }
        }

    }


    public static void main(String[] args) {

        String[][] test1 = {{"a", "b", "c"},
                {"d", "e", "f"},
                {"g", "h", "i"}};

        String[][] test2 = {{"a", "b", "c", "b"},
                {"e", "f", "g", "h"},
                {"i", "j", "k", "l"},
                {"m", "n", "o", "p"}};

        String[][] test3 = {{"a", "b", "c", "b", "e"},
                {"f", "g", "h", "i", "j"},
                {"k", "l", "m", "n", "o"},
                {"p", "q", "r", "s", "t"},
                {"u", "v", "w", "x", "y"}};
    }

}