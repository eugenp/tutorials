package com.baeldung.hexagonal.tictactoe.consoleadapters;

import java.util.Scanner;
import com.baeldung.hexagonal.tictactoe.domain.InputPort;

public class ConsoleInputAdapter implements InputPort {
    Scanner inputScanner;

    public ConsoleInputAdapter() {
        inputScanner = new Scanner(System.in);
    }

    @Override
    public int getInput(boolean playerOne) {
        System.out.print("Player " + (playerOne ? "1: " : "2 :"));
        System.out.println("Choose square");
        return inputScanner.nextInt();
    }

}