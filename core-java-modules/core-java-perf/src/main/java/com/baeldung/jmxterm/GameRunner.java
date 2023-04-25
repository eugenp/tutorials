package com.baeldung.jmxterm;

import java.util.Arrays;
import java.util.List;

public class GameRunner {

    public static void main(String[] args) {
        MBeanGameServer mBeanGameServer = new MBeanGameServer();
        Player bob = new Player("Bob");
        Player alice = new Player("Alice");
        Player john = new Player("John");
        List<Player> players = Arrays.asList(bob, alice, john);
        GuessGame guessGame = new GuessGame(players);
        mBeanGameServer.registerGame(guessGame);
        guessGame.start();
    }

}
