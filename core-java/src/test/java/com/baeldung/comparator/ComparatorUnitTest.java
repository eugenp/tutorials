package com.baeldung.comparator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ComparatorUnitTest {

    List<Player> footballTeam;

    @Before
    public void setUp() {
        footballTeam = new ArrayList<Player>();
        Player player1 = new Player(59, "John", 20);
        Player player2 = new Player(67, "Roger", 22);
        Player player3 = new Player(45, "Steven", 24);
        footballTeam.add(player1);
        footballTeam.add(player2);
        footballTeam.add(player3);
    }

    @Test
    public void whenUsingRankingComparator_thenSortedList() {
        PlayerRankingComparator playerComparator = new PlayerRankingComparator();
        Collections.sort(footballTeam, playerComparator);
        assertEquals(footballTeam.get(0)
            .getName(), "Steven");
        assertEquals(footballTeam.get(2)
            .getRanking(), 67);
    }

    @Test
    public void whenUsingAgeComparator_thenSortedList() {
        PlayerAgeComparator playerComparator = new PlayerAgeComparator();
        Collections.sort(footballTeam, playerComparator);
        assertEquals(footballTeam.get(0)
            .getName(), "John");
        assertEquals(footballTeam.get(2)
            .getRanking(), 45);
    }

}
