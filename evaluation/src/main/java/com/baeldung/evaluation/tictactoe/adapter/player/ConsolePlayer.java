package com.baeldung.evaluation.tictactoe.adapter.player;

import com.baeldung.evaluation.tictactoe.port.IPlayer;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Scanner;

public class ConsolePlayer implements IPlayer {

    private String name;
    private Character[][] grid;

    public ConsolePlayer(String name) {
        this.name = name;
    }

    @Override public String getName() {
        return this.name;
    }

    @Override public ImmutablePair<Integer, Integer> move() {
        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        int y = in.nextInt();
        return new ImmutablePair<>(x, y);
    }

    @Override public void endGame(Result result) {
        System.out.println(name + " " + result);
        if (result.equals(Result.LOSE)) {
            displayGrid();
        }
    }

    @Override public void update(Character[][] grid) {
        this.grid = grid;
        displayGrid();
    }

    private void displayGrid() {
        for (int i = 0; i < 3; i++) {
            System.out.println(grid[i][0] + "|" + grid[i][1] + "|" + grid[i][2]);
            if (i < 2)
                System.out.println("------");
        }
    }
}
