package com.baeldung.normaldesign;

import java.util.Random;
import java.util.Scanner;

public class CoinToss {
    private enum Coin {Head, Tail}

    public void play() {
        Coin guess;
        while (true) {
            display("Enter 1 for head, 2 for tail, 0 to quit:");
            int choice = readInputFromKeyboard();
            if (choice == 0) {
                break;
            } else if (choice == 1) {
                guess = Coin.Head;
            } else if (choice == 2) {
                guess = Coin.Tail;
            } else {
                display("Invalid choice! Try again!");
                continue;
            }
            Coin toss = flipCoin();
            displayTheResult(toss, guess);
        }
    }

    private void displayTheResult(Coin toss, Coin guess) {
        if (guess == toss) {
            display("You won the toss!");
        } else {
            display("Sorry! You lost the toss.");
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

    private int readInputFromKeyboard() {
        Scanner keyboard = new Scanner(System.in);
        return keyboard.nextInt();
    }

    private void display(String text) {
        System.out.println(text);
    }
}
