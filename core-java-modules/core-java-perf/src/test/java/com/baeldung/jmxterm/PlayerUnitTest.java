package com.baeldung.jmxterm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerUnitTest {

    @Test
    void givenNewPlayer_thenScoreIsZero() {
        Player player = new Player("John");
        assertEquals(0, player.getScore());
    }

    @Test
    void givenNewPlayer_thenGuessIsZero() {
        Player player = new Player("John");
        assertEquals(0, player.getGuess());
    }

    @Test
    void givenNewPlayer_whenIncrementScore_thenScoreIsOne() {
        Player player = new Player("John");
        player.incrementScore();
        assertEquals(1, player.getScore());
    }
}