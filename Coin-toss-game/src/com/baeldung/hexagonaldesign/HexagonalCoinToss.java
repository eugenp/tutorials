package com.baeldung.hexagonaldesign;

import java.util.Random;

public class HexagonalCoinToss {
    private UserInput userInput;
    private Display display;

    public HexagonalCoinToss(final Display display, final UserInput userInput) {
        this.display=display;
        this.userInput=userInput;
    }

    public void play() {
        Coin guess;
        while (true) {
            display.show("Enter 1 for head, 2 for tail, 0 to quit:");
            int choice = userInput.input();
            if (choice == 0) {
                break;
            } else if (choice == 1) {
                guess = Coin.Head;
            } else if (choice == 2) {
                guess = Coin.Tail;
            } else {
                display.show("Invalid choice! Try again!");
                continue;
            }
            Coin toss = flipCoin();
            displayTheResult(toss, guess);
        }
    }

    private Coin flipCoin() {
        Random r = new Random();
        int i = r.nextInt(2);
        if (i == 0) {
            return Coin.Head;
        } else {
            return Coin.Tail;
        }
    }

    private void displayTheResult(Coin toss, Coin guess) {
        if (guess == toss) {
            display.show("You won the toss!");
        } else {
            display.show("Sorry! You lost the toss.");
        }
    }
}
