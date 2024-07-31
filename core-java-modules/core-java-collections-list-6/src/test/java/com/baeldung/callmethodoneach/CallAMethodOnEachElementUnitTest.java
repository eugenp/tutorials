package com.baeldung.callmethodoneach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

class Player {

    private int id;
    private String name;
    private int score;

    public Player(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}

public class CallAMethodOnEachElementUnitTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    // @formatter:off
    private final static List<Player> PLAYERS = List.of(
        new Player(1, "Kai", 42),
        new Player(2, "Eric", 43),
        new Player(3, "Saajan", 64),
        new Player(4, "Kevin", 30),
        new Player(5, "John", 5));
    // @formatter:on

    @Test
    void whenUsingForLoopCallLogPrint_thenGetExpectedResult() {
        for (Player p : PLAYERS) {
            log.info(p.getName());
        }
    }

    @Test
    void whenUsingForEachCallLogPrint_thenGetExpectedResult() {
        PLAYERS.forEach(player -> log.info(player.getName()));
    }

    @Test
    void whenUsingForLoopAndListAddExtractPlayerNames_thenGetExpectedResult() {
        List<String> names = new ArrayList<>();
        for (Player p : PLAYERS) {
            names.add(p.getName());
        }
        assertEquals(Arrays.asList("Kai", "Eric", "Saajan", "Kevin", "John"), names);
    }

    @Test
    void whenUsingGuavaTransformExtractPlayerNames_thenGetExpectedResult() {
        List<String> names = Lists.transform(PLAYERS, new Function<Player, String>() {
            @Override
            public String apply(Player input) {
                return input.getName();
            }
        });

        assertEquals(Arrays.asList("Kai", "Eric", "Saajan", "Kevin", "John"), names);
    }

    @Test
    void whenUsingStreamMapExtractPlayerNames_thenGetExpectedResult() {
        List<String> names = PLAYERS.stream()
            .map(player -> player.getName())
            .collect(Collectors.toList());
        assertEquals(List.of("Kai", "Eric", "Saajan", "Kevin", "John"), names);
    }

}