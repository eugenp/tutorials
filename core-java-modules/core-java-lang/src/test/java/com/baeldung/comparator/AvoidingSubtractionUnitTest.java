package com.baeldung.comparator;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AvoidingSubtractionUnitTest {

    @Test
    public void givenTwoPlayers_whenUsingSubtraction_thenOverflow() {
        Comparator<Player> comparator = (p1, p2) -> p1.getRanking() - p2.getRanking();
        Player player1 = new Player(59, "John", Integer.MAX_VALUE);
        Player player2 = new Player(67, "Roger", -1);

        List<Player> players = Arrays.asList(player1, player2);
        players.sort(comparator);
        System.out.println(players);

        assertEquals("John", players.get(0).getName());
        assertEquals("Roger", players.get(1).getName());
    }
}
