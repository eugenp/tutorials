package com.baeldung.map.changekey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class Player {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

public class HashmapChangeKeyUnitTest {

    @Test
    void whenRemoveThenPutWithTheNewKey_thenGetExpectedResult() {
        Map<String, Integer> playerMap = new HashMap<>();

        playerMap.put("Kai", 42);
        playerMap.put("Amanda", 88);
        playerMap.put("Tom", 200);

        // now replace Kai with Eric
        playerMap.put("Eric", playerMap.remove("Kai"));

        assertFalse(playerMap.containsKey("Kai"));
        assertTrue(playerMap.containsKey("Eric"));
        assertEquals(42, playerMap.get("Eric"));
    }

    @Test
    void whenChangeTheKey_thenMayNotGetExpectedResult() {
        Map<Player, Integer> myMap = new HashMap<>();
        Player kai = new Player("Kai");
        Player tom = new Player("Tom");
        Player amanda = new Player("Amanda");

        myMap.put(kai, 42);
        myMap.put(amanda, 88);
        myMap.put(tom, 200);

        assertTrue(myMap.containsKey(kai));

        //change Kai's name to Eric
        kai.setName("Eric");
        assertEquals("Eric", kai.getName());

        Player eric = new Player("Eric");
        assertEquals(eric, kai);

        // the map contains neither Kai nor Eric:
        assertFalse(myMap.containsKey(kai));
        assertFalse(myMap.containsKey(eric));

        // although the Player("Eric") exists:
        long ericCount = myMap.keySet()
          .stream()
          .filter(player -> player.getName()
            .equals("Eric"))
          .count();

        assertEquals(1, ericCount);
    }
}