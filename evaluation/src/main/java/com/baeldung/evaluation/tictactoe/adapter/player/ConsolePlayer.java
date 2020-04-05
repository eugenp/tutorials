package com.baeldung.evaluation.tictactoe.adapter.player;

import com.baeldung.evaluation.tictactoe.port.TicTacToeGame;
import com.baeldung.evaluation.tictactoe.port.Player;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import com.baeldung.evaluation.tictactoe.port.TicTacToeGame.GameResult;

import java.util.Observable;
import java.util.Scanner;

public class ConsolePlayer extends Player {
    private Character[][] grid;
    private boolean gameOver;

    public ConsolePlayer(TicTacToeGame game) {
        super(game);
    }

    public void consoleMove() {
        if (grid == null)
            System.out.print("Please make the first move: ");
        else
            System.out.print("Please make a move: ");

        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        int y = in.nextInt();
        move(x, y);
    }

    private void displayGrid() {
        for (int i = 0; i < 3; i++) {
            System.out.println(grid[i][0] + "|" + grid[i][1] + "|" + grid[i][2]);
            if (i < 2)
                System.out.println("------");
        }
    }

    @Override public void update(Observable o, Object update) {
        ImmutableTriple gameState = (ImmutableTriple) update;
        Player lastMovePlayer = (Player) gameState.left;
        grid = (Character[][]) gameState.middle;
        if (grid != null && !lastMovePlayer.equals(this))
            displayGrid();
        GameResult result = (GameResult) gameState.right;
        gameOver = (result != GameResult.PLAYING);
        if (result.toString().startsWith("WIN") && lastMovePlayer.equals(this))
            System.out.println(result.toString());
        if (result.toString().startsWith("DRAW"))
            System.out.println(result.toString());
    }

    @Override public boolean canMove() {
        return !gameOver;
    }
}
