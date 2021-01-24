package com.baeldung.compareto;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

public class FootballPlayerUnitTest {

    @Test
    public void givenInconsistentCompareToAndEqualsImpl_whenUsingSortedSet_thenElementsAreNotAdded() {
        FootballPlayer messi = new FootballPlayer("Messi", 800);
        FootballPlayer ronaldo = new FootballPlayer("Ronaldo", 800);

        TreeSet<FootballPlayer> set = new TreeSet<>();
        set.add(messi);
        set.add(ronaldo);

        assertThat(set).hasSize(1);
        assertThat(set).doesNotContain(ronaldo);
    }

    @Test
    public void givenCompareToImpl_whenSavingElementsInTreeMap_thenKeysAreSortedUsingCompareTo() {
        FootballPlayer ronaldo = new FootballPlayer("Ronaldo", 900);
        FootballPlayer messi = new FootballPlayer("Messi", 800);
        FootballPlayer modric = new FootballPlayer("modric", 100);

        Map<FootballPlayer, String> players = new TreeMap<>();
        players.put(ronaldo, "forward");
        players.put(messi, "forward");
        players.put(modric, "midfielder");

        assertThat(players.keySet()).containsExactly(modric, messi, ronaldo);
    }

}
