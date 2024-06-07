package com.baeldung.hibernate.joinfetchcriteriaquery;

import com.baeldung.hibernate.joinfetchcriteriaquery.model.League;
import com.baeldung.hibernate.joinfetchcriteriaquery.model.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JoinFetchApplicationUnitTest {

    @Test
    void givenPlayersInPremierLeague_whenJoinFetchMethodCalled_thenReturnCorrectNumberOfPlayers() {
        Player saka = new Player(90, "Saka", "Arsenal", 22, new League(6, "Premier League"));
        Player rice = new Player(91, "Mike", "Arsenal", 30, new League(6, "Premier League"));

        List<Player> actualPlayers = JoinFetchApplicationView.joinFetch();

        assertEquals(2, actualPlayers.size());
    }
}