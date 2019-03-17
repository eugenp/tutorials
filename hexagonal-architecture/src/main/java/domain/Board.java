package domain;

public class Board {
    public enum Symbol { CIRCLE, CROSS, NONE };

    private Symbol[][] grid = new Symbol[][] {
        { Symbol.NONE, Symbol.NONE, Symbol.NONE },
        { Symbol.NONE, Symbol.NONE, Symbol.NONE },
        { Symbol.NONE, Symbol.NONE, Symbol.NONE }
    };

    public void reinit() {
        for (Symbol[] row : grid)
            for (int i = 0; i < row.length; i++)
                row[i] = Symbol.NONE;
    }

    public void place(Symbol symbol, int row, int col) throws PositionUnavailableException {
        if (!grid[row][col].equals(Symbol.NONE))
            throw new PositionUnavailableException(String.format("Failed to place symbol at position (%d, %d): already occupied by %s.", row, col, grid[row][col]));

        grid[row][col] = symbol;
    }

    public Symbol pos(int row, int col) {
        return grid[row][col];
    }

    public int getRowCount() {
        return grid.length;
    }

    public int getColCount() {
        return grid[0].length;
    }
}
