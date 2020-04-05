package com.baeldung.evaluation.tictactoe.port;

import java.util.Observable;

public abstract class TicTacToeGame extends Observable {
    public enum GameResult {PLAYING, WIN_X, WIN_O, DRAW}

    // Player port
    public abstract void join(Player player);

    public abstract void move(Player player, int row, int column);

    // Logger port
    public abstract void setLogger(ILog logger);
}
