package com.baeldung.hexagonal.tictactoe.domain;

public interface DisplayPort {

    void showGrid(int[][] table);

    void showInvalidEntry();

    void showResult(int status);

}