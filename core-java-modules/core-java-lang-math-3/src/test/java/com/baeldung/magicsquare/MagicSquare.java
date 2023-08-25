package com.baeldung.magicsquare;

import org.junit.platform.commons.util.StringUtils;

import java.util.stream.IntStream;

public class MagicSquare {
    private int[][] cells;

    public MagicSquare(int n) {
        this.cells = new int[n][];

        for (int i = 0; i < n; ++i) {
            this.cells[i] = new int[n];
        }

        if (n % 2 == 1) {
            populateOdd();
        } else if (n % 4 == 0) {
            populateDoubleEven();
        } else if (n >= 6) {
            populateSingleEven();
        }
    }

    private void populateOdd() {
        int n = getN();

        populateOddArea(0, 0, n, 0);
    }

    private void populateOddArea(int xOffset, int yOffset, int n, int numberOffset) {
        int y = 0;
        int x = (n - 1) / 2;
        setCell(xOffset + x, yOffset + y, numberOffset + 1);

        for (int number = 2; number <= n * n; ++number) {
            int nextX = x + 1;
            if (nextX == n) {
                nextX = 0;
            }

            int nextY = y - 1;
            if (nextY == -1) {
                nextY = n - 1;
            }

            if (getCell(xOffset + nextX, yOffset + nextY) != 0) {
                nextX = x;

                nextY = y + 1;
                if (nextY == n) {
                    nextY = 0;
                }
            }

            setCell(xOffset + nextX, yOffset + nextY, numberOffset + number);

            x = nextX;
            y = nextY;
        }
    }

    private void populateDoubleEven() {
        int n = getN();
        int number = 1;

        for (int y = 0; y < n; ++y) {
            for (int x = 0; x < n; ++x) {
                boolean highlighted = false;

                if ((y < n/4 || y >= 3*n/4) && (x >= n/4 && x < 3*n/4)) {
                    highlighted = true;
                } else if ((x < n/4 || x >= 3*n/4) && (y >= n/4 && y < 3*n/4)) {
                    highlighted = true;
                }

                if (highlighted) {
                    setCell(x, y, (n * n) - number + 1);
                } else {
                    setCell(x, y, number);
                }

                number += 1;
            }
        }
    }

    private void populateSingleEven() {
        int n = getN();
        int halfN = n/2;
        int swapSize = (int) n/4;

        populateOddArea(0, 0, halfN, 0);
        populateOddArea(halfN, halfN, halfN, halfN * halfN);
        populateOddArea(halfN, 0, halfN, (halfN * halfN) * 2);
        populateOddArea(0, halfN, halfN, (halfN * halfN) * 3);

        for (int x = 0; x < swapSize; ++x) {
            swapCells(x, 0, x, halfN);
            swapCells(x, halfN - 1, x, n - 1);

            for (int y = 1; y < halfN - 1; ++y) {
                swapCells(x + 1, y, x + 1, y + halfN);
            }
        }

        for (int x = 0; x < swapSize - 1; ++x) {
            for (int y = 0; y < halfN; ++y) {
                swapCells(n - x - 1, y, n - x - 1, y + halfN);
            }
        }

    }

    private void swapCells(int x1, int y1, int x2, int y2) {
        int cell1 = getCell(x1, y1);
        int cell2 = getCell(x2, y2);

        setCell(x1, y1, cell2);
        setCell(x2, y2, cell1);
    }

    public int getN() {
        return cells.length;
    }

    public int getCell(int x, int y) {
        return cells[x][y];
    }

    public void setCell(int x, int y, int value) {
        cells[x][y] = value;
    }

    public void validate() {
        int n = getN();
        int expectedValue = ((n * n * n) + n) / 2;

        // Diagonals
        if (IntStream.range(0, n).map(i -> getCell(i, i)).sum() != expectedValue) {
            throw new IllegalStateException("Leading diagonal is not the expected value");
        }
        if (IntStream.range(0, n).map(i -> getCell(i, n - i - 1)).sum() != expectedValue) {
            throw new IllegalStateException("Trailing diagonal is not the expected value");
        }

        // Rows
        IntStream.range(0, n).forEach(y -> {
            if (IntStream.range(0, n).map(x -> getCell(x, y)).sum() != expectedValue) {
                throw new IllegalStateException("Row is not the expected value");
            }
        });

        // Cols
        IntStream.range(0, n).forEach(x -> {
            if (IntStream.range(0, n).map(y -> getCell(x, y)).sum() != expectedValue) {
                throw new IllegalStateException("Column is not the expected value");
            }
        });
    }

    public String toString() {
        int n = getN();
        int largestNumberLength = Integer.toString(n * n).length();
        String formatString = " %0" + largestNumberLength + "d ";

        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < n; ++y) {
            for (int x = 0; x < n; ++x) {
                int value = getCell(x, y);
                if (value == 0) {
                    sb.append(" ");
                    for (int i = 0; i < largestNumberLength; ++i) {
                        sb.append(".");
                    }
                    sb.append(" ");
                } else {
                    sb.append(String.format(formatString, value));
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
