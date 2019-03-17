package framework;

import domain.Board;
import port.IBoardDisplayer;

public class StdOutBoardDisplayer implements IBoardDisplayer {

    @Override
    public void display(Board board) {
        System.out.println("     0   1   2");
        System.out.println("  -------------");
        for (int row = 0; row < board.getRowCount(); row++) {
            System.out.println(String.format("%d | %s | %s | %s |", row, showSymbol(board.pos(row, 0)), showSymbol(board.pos(row, 1)), showSymbol(board.pos(row, 2))));
            System.out.println("  -------------");
        }
        System.out.println();
    }

    private String showSymbol(Board.Symbol symbol) {
        switch (symbol) {
            case CROSS: return "X";
            case CIRCLE: return "O";
            case NONE:
            default: return " ";
        }
    }
}
