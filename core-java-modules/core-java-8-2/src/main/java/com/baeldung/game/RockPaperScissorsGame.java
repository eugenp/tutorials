package com.baeldung.game;

import java.util.*;

class RockPaperScissorsGame {

    enum Move {
        ROCK("rock"),
        PAPER("paper"),
        SCISSORS("scissors");

        private String value;

        Move(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

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

            if (Arrays.stream(Move.values()).noneMatch(x -> x.getValue().equals(playerMove))) {
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
        return playerMove.equals(Move.ROCK.value) && computerMove.equals(Move.SCISSORS.value)
                || (playerMove.equals(Move.SCISSORS.value) && computerMove.equals(Move.PAPER.value))
                || (playerMove.equals(Move.PAPER.value) && computerMove.equals(Move.ROCK.value));
    }

    private static String getComputerMove() {
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        String computerMove = Move.values()[randomNumber].getValue();
        System.out.println("Computer move: " + computerMove);
        return computerMove;
    }
}