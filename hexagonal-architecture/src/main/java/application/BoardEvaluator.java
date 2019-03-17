package application;

import domain.Board;

public class BoardEvaluator {

    public Board.Symbol evaluateWinner(Board board) {
        Board.Symbol winningSymbol;

        for (int i = 0; i <= 2; i++) {
            winningSymbol = checkSameAndGetSymbolForRow(i, board);
            if (!Board.Symbol.NONE.equals(winningSymbol)) {
                return winningSymbol;
            }

            winningSymbol = checkSameAndGetSymbolForCol(i, board);
            if (!Board.Symbol.NONE.equals(winningSymbol)) {
                return winningSymbol;
            }
        }

        winningSymbol = checkSameAndGetSymbolForDiagonal(board);
        return winningSymbol;
    }


    private Board.Symbol checkSameAndGetSymbolForRow(int row, Board board) {
        if (board.pos(row, 0).equals(board.pos(row, 1)) && board.pos(row, 1).equals(board.pos(row, 2))) {
            return board.pos(row, 0);
        }
        return Board.Symbol.NONE;
    }


    private Board.Symbol checkSameAndGetSymbolForCol(int col, Board board) {
        if (board.pos(0, col).equals(board.pos(1, col)) && board.pos(1, col).equals(board.pos(2, col))) {
            return board.pos(0, col);
        }
        return Board.Symbol.NONE;
    }


    private Board.Symbol checkSameAndGetSymbolForDiagonal(Board board) {
        if (board.pos(0, 0).equals(board.pos(1, 1)) && board.pos(1, 1).equals(board.pos(2, 2))) {
            return board.pos(0, 0);
        }
        if (board.pos(0, 2).equals(board.pos(1, 1)) && board.pos(1, 1).equals(board.pos(2, 0))) {
            return board.pos(0, 2);
        }
        return Board.Symbol.NONE;
    }
}
