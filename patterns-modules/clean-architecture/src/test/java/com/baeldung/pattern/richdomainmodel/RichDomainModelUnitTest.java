package com.baeldung.pattern.richdomainmodel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class RichDomainModelUnitTest {

    @Test
    public void givenATennisGame_whenReceiverWinsThreePoints_thenScoreIsFortyLove() {
        TennisGame game = new TennisGame("server", "receiver");

        game.wonPoint("server");
        game.wonPoint("server");
        game.wonPoint("server");

        assertThat(game.getScore())
          .isEqualTo("Forty-Love");
    }

    @Test
    public void givenATennisGame_whenEachPlayerWonTwoPoints_thenScoreIsThirtyAll() {
        TennisGame game = new TennisGame("server", "receiver");

        game.wonPoint("server");
        game.wonPoint("server");
        game.wonPoint("receiver");
        game.wonPoint("receiver");

        assertThat(game.getScore())
          .isEqualTo("Thirty-All");
    }
}