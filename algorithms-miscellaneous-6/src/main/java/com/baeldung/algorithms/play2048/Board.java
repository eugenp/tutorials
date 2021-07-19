package com.baeldung.algorithms.play2048;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Board {
    private static final Logger LOG = LoggerFactory.getLogger(Board.class);

    private final int[][] board;

    private final int score;

    public Board(int size) {
        assert(size > 0);

        this.board = new int[size][];
        this.score = 0;

        for (int x = 0; x < size; ++x) {
            this.board[x] = new int[size];
            for (int y = 0; y < size; ++y) {
                board[x][y] = 0;
            }
        }
    }

    private Board(int[][] board, int score) {
        this.score = score;
        this.board = new int[board.length][];

        for (int x = 0; x < board.length; ++x) {
            this.board[x] = Arrays.copyOf(board[x], board[x].length);
        }
    }

    public int getSize() {
        return board.length;
    }

    public int getScore() {
        return score;
    }

    public int getCell(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        assert(x >= 0 && x < board.length);
        assert(y >= 0 && y < board.length);

        return board[x][y];
    }

    public boolean isEmpty(Cell cell) {
        return getCell(cell) == 0;
    }

    public List<Cell> emptyCells() {
        List<Cell> result = new ArrayList<>();
        for (int x = 0; x < board.length; ++x) {
            for (int y = 0; y < board[x].length; ++y) {
                Cell cell = new Cell(x, y);
                if (isEmpty(cell)) {
                    result.add(cell);
                }
            }
        }
        return result;
    }

    public Board placeTile(Cell cell, int number) {
        if (!isEmpty(cell)) {
            throw new IllegalArgumentException("That cell is not empty");
        }

        Board result = new Board(this.board, this.score);
        result.board[cell.getX()][cell.getY()] = number;
        return result;
    }

    public Board move(Move move) {
        // Clone the board
        int[][] tiles = new int[this.board.length][];
        for (int x = 0; x < this.board.length; ++x) {
            tiles[x] = Arrays.copyOf(this.board[x], this.board[x].length);
        }

        LOG.debug("Before move: {}", Arrays.deepToString(tiles));
        // If we're doing an Left/Right move then transpose the board to make it a Up/Down move
        if (move == Move.LEFT || move == Move.RIGHT) {
            tiles = transpose(tiles);
            LOG.debug("After transpose: {}", Arrays.deepToString(tiles));
        }
        // If we're doing a Right/Down move then reverse the board.
        // With the above we're now always doing an Up move
        if (move == Move.DOWN || move == Move.RIGHT) {
            tiles = reverse(tiles);
            LOG.debug("After reverse: {}", Arrays.deepToString(tiles));
        }
        LOG.debug("Ready to move: {}", Arrays.deepToString(tiles));

        // Shift everything up
        int[][] result = new int[tiles.length][];
        int newScore = 0;
        for (int x = 0; x < tiles.length; ++x) {
            LinkedList<Integer> thisRow = new LinkedList<>();
            for (int y = 0; y < tiles[0].length; ++y) {
                if (tiles[x][y] > 0) {
                    thisRow.add(tiles[x][y]);
                }
            }

            LOG.debug("Unmerged row: {}", thisRow);
            LinkedList<Integer> newRow = new LinkedList<>();
            while (thisRow.size() >= 2) {
                int first = thisRow.pop();
                int second = thisRow.peek();
                LOG.debug("Looking at numbers {} and {}", first, second);
                if (second == first) {
                    LOG.debug("Numbers match, combining");
                    int newNumber = first * 2;
                    newRow.add(newNumber);
                    newScore += newNumber;
                    thisRow.pop();
                } else {
                    LOG.debug("Numbers don't match");
                    newRow.add(first);
                }
            }
            newRow.addAll(thisRow);
            LOG.debug("Merged row: {}", newRow);

            result[x] = new int[tiles[0].length];
            for (int y = 0; y < tiles[0].length; ++y) {
                if (newRow.isEmpty()) {
                    result[x][y] = 0;
                } else {
                    result[x][y] = newRow.pop();
                }
            }
        }
        LOG.debug("After moves: {}", Arrays.deepToString(result));

        // Un-reverse the board
        if (move == Move.DOWN || move == Move.RIGHT) {
            result = reverse(result);
            LOG.debug("After reverse: {}", Arrays.deepToString(result));
        }
        // Un-transpose the board
        if (move == Move.LEFT || move == Move.RIGHT) {
            result = transpose(result);
            LOG.debug("After transpose: {}", Arrays.deepToString(result));
        }
        return new Board(result, this.score + newScore);
    }

    private static int[][] transpose(int[][] input) {
        int[][] result = new int[input.length][];

        for (int x = 0; x < input.length; ++x) {
            result[x] = new int[input[0].length];
            for (int y = 0; y < input[0].length; ++y) {
                result[x][y] = input[y][x];
            }
        }

        return result;
    }

    private static int[][] reverse(int[][] input) {
        int[][] result = new int[input.length][];

        for (int x = 0; x < input.length; ++x) {
            result[x] = new int[input[0].length];
            for (int y = 0; y < input[0].length; ++y) {
                result[x][y] = input[x][input.length - y - 1];
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(board);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board1 = (Board) o;
        return Arrays.deepEquals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}
