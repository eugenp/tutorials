package com.baeldung.evaluation.tictactoe.port;

import java.util.Observer;

public abstract class Player implements Observer {

    protected TicTacToeGame game;

    public Player(TicTacToeGame game) {
        this.game = game;
        this.game.join(this);
    }

    public abstract boolean canMove();

    protected void move(int x, int y) {
        game.move(this, x, y);
    }
}
