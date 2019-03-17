package application;

import domain.Board;
import domain.Player;
import domain.PositionUnavailableException;
import port.IBoardDisplayer;
import port.ILoggingDevice;
import port.IMessageOutDevice;

public class Game {
    private Player player1;
    private Player player2;
    private IBoardDisplayer boardDisplayer = IBoardDisplayer.NO_DISPLAYER;
    private ILoggingDevice loggingDevice = ILoggingDevice.NO_DEVICE;
    private IMessageOutDevice messageOutDevice = IMessageOutDevice.NO_DEVICE;

    private Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public static Game create(Player player1, Player player2) {
        return new Game(player1, player2);
    }

    public Game useBoardDisplay(IBoardDisplayer boardDisplayer) {
        this.boardDisplayer = boardDisplayer;
        return this;
    }

    public Game useLoggingDevice(ILoggingDevice loggingDevice) {
        this.loggingDevice = loggingDevice;
        return this;
    }

    public Game useMessageOutDevice(IMessageOutDevice messageOutDevice) {
        this.messageOutDevice = messageOutDevice;
        return this;
    }

    public void start() {
        Board board = new Board();
        BoardEvaluator boardEvaluator = new BoardEvaluator();

        boolean allowToSwitchPlayer = true;
        Player player = player2;
        Board.Symbol winningSymbol = Board.Symbol.NONE;

        messageOutDevice.out("Game start!");
        do {
            boardDisplayer.display(board);

            player = !allowToSwitchPlayer ? player : (player.equals(player1) ? player2 : player1);
            messageOutDevice.out(String.format("Player %s's decision: ", player.getName()));
            int[] decision = player.decide();

            try {
                int row = decision[0];
                int col = decision[1];
                board.place(player.getSymbol(), row, col);
                winningSymbol = boardEvaluator.evaluateWinner(board);
                allowToSwitchPlayer = true;

                messageOutDevice.out(String.format("Player %s has made move (%d, %d).", player.getName(), row, col));
            }
            catch (PositionUnavailableException e) {
                messageOutDevice.out(String.format("Invalid move. %s", e.getMessage()));
                loggingDevice.log(e.getMessage(), e);
                allowToSwitchPlayer = false;
            }
        } while (winningSymbol.equals(Board.Symbol.NONE));

        boardDisplayer.display(board);
        messageOutDevice.outWinningSymbol(winningSymbol);
    }
}
