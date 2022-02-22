package com.baeldung.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

class RockPaperScissorsGame {

    private static Map<Integer, String> movesMap = new HashMap<Integer, String>() {{
        put(0, "rock");
        put(1, "paper");
        put(2, "scissors");
    }};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int wins = 0;
        int losses = 0;

        System.out.println("Welcome to Rock-Paper-Scissors! Please enter \"rock\", \"paper\", \"scissors\", or \"quit\" to exit.");

        while (true) {
            System.out.println("-------------------------");
            System.out.print("Enter your move: ");
            String playerMove = scanner.nextLine();

            if (playerMove.equals("quit")) {
                System.out.println("You won " + wins + " times and lost " + losses + " times.");
                System.out.println("Thanks for playing! See you again.");
                break;
            }

            if (!movesMap.containsValue(playerMove)) {
                System.out.println("Your move isn't valid!");
                continue;
            }

            String computerMove = getComputerMove();

            if (playerMove.equals(computerMove)) {
                System.out.println("It's a tie!");
            } else if (isPlayerWin(playerMove, computerMove)) {
                System.out.println("You won!");
                wins++;
            } else {
                System.out.println("You lost!");
                losses++;
            }
        }
    }

    private static boolean isPlayerWin(String playerMove, String computerMove) {
        return playerMove.equals("rock") && computerMove.equals("scissors")
                || (playerMove.equals("scissors") && computerMove.equals("paper"))
                || (playerMove.equals("paper") && computerMove.equals("rock"));
    }

    private static String getComputerMove() {
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        String computerMove = movesMap.get(randomNumber);
        System.out.println("Computer move: " + computerMove);
        return computerMove;
    }
}