package com.baeldung.map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;

class Player {
    private String name;
    private Integer score = 0;

    public Player(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }

        Player player = (Player) o;

        if (!Objects.equals(name, player.name)) {
            return false;
        }
        return Objects.equals(score, player.score);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}

public class LinkedHashMapSortByValueUnitTest {
    private static LinkedHashMap<String, Integer> MY_MAP = new LinkedHashMap<>();

    static {
        MY_MAP.put("key a", 4);
        MY_MAP.put("key b", 1);
        MY_MAP.put("key c", 3);
        MY_MAP.put("key d", 2);
        MY_MAP.put("key e", 5);
    }

    private static LinkedHashMap<String, Integer> EXPECTED_MY_MAP = new LinkedHashMap<>();

    static {
        EXPECTED_MY_MAP.put("key b", 1);
        EXPECTED_MY_MAP.put("key d", 2);
        EXPECTED_MY_MAP.put("key c", 3);
        EXPECTED_MY_MAP.put("key a", 4);
        EXPECTED_MY_MAP.put("key e", 5);
    }

    private static final LinkedHashMap<String, Player> PLAYERS = new LinkedHashMap<>();
    static {
        PLAYERS.put("player a", new Player("Eric", 9));
        PLAYERS.put("player b", new Player("Kai", 7));
        PLAYERS.put("player c", new Player("Amanda", 20));
        PLAYERS.put("player d", new Player("Kevin", 4));
    }

    private static final LinkedHashMap<String, Player> EXPECTED_PLAYERS = new LinkedHashMap<>();

    static {
        EXPECTED_PLAYERS.put("player d", new Player("Kevin", 4));
        EXPECTED_PLAYERS.put("player b", new Player("Kai", 7));
        EXPECTED_PLAYERS.put("player a", new Player("Eric", 9));
        EXPECTED_PLAYERS.put("player c", new Player("Amanda", 20));
    }

    @Test
    void whenUsingCollectionSort_thenGetExpectedResult() {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(MY_MAP.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue()
                  .compareTo(o2.getValue());
            }
        });

        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> e : entryList) {
            result.put(e.getKey(), e.getValue());
        }
        assertEquals(EXPECTED_MY_MAP, result);
    }

    @Test
    void whenUsingEntrycomparingByValueAndFillAMap_thenGetExpectedResult() {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        MY_MAP.entrySet()
          .stream()
          .sorted(Map.Entry.comparingByValue())
          .forEachOrdered(entry -> result.put(entry.getKey(), entry.getValue()));
        assertEquals(EXPECTED_MY_MAP, result);
    }

    @Test
    void whenUsingEntrycomparingByValueAndCollect_thenGetExpectedResult() {
        LinkedHashMap<String, Integer> result = MY_MAP.entrySet()
          .stream()
          .sorted(Map.Entry.comparingByValue())
          .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), Map::putAll);
        assertEquals(EXPECTED_MY_MAP, result);
    }

    @Test
    void whenUsingEntrycomparingByValueAndComparator_thenGetExpectedResult() {
        LinkedHashMap<String, Player> result = PLAYERS.entrySet()
          .stream()
          .sorted(Map.Entry.comparingByValue(Comparator.comparing(Player::getScore)))
          .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), Map::putAll);
        assertEquals(EXPECTED_PLAYERS, result);
    }

}