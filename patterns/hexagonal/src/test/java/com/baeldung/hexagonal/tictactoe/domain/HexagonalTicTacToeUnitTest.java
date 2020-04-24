package com.baeldung.hexagonal.tictactoe.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.baeldung.hexagonal.tictactoe.domain.DisplayPort;
import com.baeldung.hexagonal.tictactoe.domain.InputPort;
import com.baeldung.hexagonal.tictactoe.domain.TicTacToe;

public class HexagonalTicTacToeUnitTest {
    static class MockInput implements InputPort {
        Iterator<Integer> inputs;

        public MockInput(List<Integer> inputs) {
            this.inputs = inputs.iterator();
        }

        @Override
        public int getInput(boolean playerOne) throws IllegalArgumentException {
            if (inputs.hasNext())
                return inputs.next();
            throw new IllegalArgumentException("Invalid input");
        }
    }

    static class MockDisplay implements DisplayPort {
        private Integer status;

        public void showGrid(int[][] table) {
        }

        public void showInvalidEntry() {
        }

        public void showResult(int status) {
            this.status = status;
        }

        public Integer getStatus() {
            return status;
        }
    }

    @Test
    public void whenColumnFull_thenPlayerWins() {
        Integer[] inputs = { 1, 2, 4, 3, 7 };
        MockDisplay display = new MockDisplay();
        TicTacToe ticTacToe = new TicTacToe(display);
        MockInput input = new MockInput(Arrays.asList(inputs));
        ticTacToe.play(input);
        Integer status = display.getStatus();
        assertNotNull("The game did not end!", status);
        assertEquals("Player 1 didn't win", 1, status.intValue());
    }

}
