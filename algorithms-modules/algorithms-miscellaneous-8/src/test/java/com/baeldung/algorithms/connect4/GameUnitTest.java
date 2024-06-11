package com.baeldung.algorithms.connect4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameUnitTest {
    @Test
    public void blankGame() {
        GameBoard gameBoard = new GameBoard(8, 6);

        System.out.println(gameBoard);
    }

    @Test
    public void playedGame() {
        GameBoard gameBoard = new GameBoard(8, 6);

        assertFalse(gameBoard.move(3, Piece.PLAYER_1));
        assertFalse(gameBoard.move(2, Piece.PLAYER_2));

        assertFalse(gameBoard.move(4, Piece.PLAYER_1));
        assertFalse(gameBoard.move(3, Piece.PLAYER_2));

        assertFalse(gameBoard.move(5, Piece.PLAYER_1));
        assertFalse(gameBoard.move(6, Piece.PLAYER_2));

        assertFalse(gameBoard.move(5, Piece.PLAYER_1));
        assertFalse(gameBoard.move(4, Piece.PLAYER_2));

        assertFalse(gameBoard.move(5, Piece.PLAYER_1));
        assertFalse(gameBoard.move(5, Piece.PLAYER_2));

        assertFalse(gameBoard.move(6, Piece.PLAYER_1));
        assertTrue(gameBoard.move(4, Piece.PLAYER_2));

        System.out.println(gameBoard);
    }
}
