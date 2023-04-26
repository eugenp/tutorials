package com.baeldung.jmxterm;

import static com.baeldung.jmxterm.RandomNumbergenerator.generateRandomNumber;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuessGame extends BroadcastingGuessGame {


    public static final int SECOND = 1000;
    private static final Logger log = Logger.getLogger(GuessGame.class.getCanonicalName());
    private final List<Player> players;
    private volatile boolean isFinished = false;
    private volatile boolean isPaused = false;


    public GuessGame(List<Player> players) {
        this.players = players;
        log.setLevel(Level.WARNING);
    }

    public void start() {
        int randomNumber = generateRandomNumber();
        while (!isFinished) {
            waitASecond();
            while (!isPaused && !isFinished) {
                log.info("Current random number is " + randomNumber);
                waitASecond();
                for (Player player : players) {
                    int guess = player.guessNumber();
                    if (guess == randomNumber) {
                        log.info("Players " + player.getName() + " " + guess + " is correct");
                        player.incrementScore();
                        notifyAboutWinner(player);
                        randomNumber = generateRandomNumber();
                        break;
                    }
                    log.info("Player " + player.getName() + " guessed incorrectly with " + guess);
                }
                log.info("\n");
            }
            if (isPaused) {
                log.info("Game is paused");
            }
            if (isFinished) {
                log.info("Game is finished");
            }
        }
    }

    @Override
    public void finishGame() {
        isFinished = true;
    }

    @Override
    public void pauseGame() {
        isPaused = true;
    }

    @Override
    public void unpauseGame() {
        isPaused = false;
    }

    public List<Player> getPlayers() {
        return players;
    }

    private void waitASecond() {
        try {
            Thread.sleep(SECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
