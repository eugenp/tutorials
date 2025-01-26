package com.baeldung.supersuper;

import java.util.ArrayList;
import java.util.List;

class Player {

    private String name;
    private String type;
    private int rank;

    public Player(String name, String type, int rank) {
        this.name = name;
        this.type = type;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getRank() {
        return rank;
    }

}

class Players {

    private List<Player> players = new ArrayList();

    protected boolean add(Player player) {
        return players.add(player);
    }
}

class FootballPlayers extends Players {

    @Override
    protected boolean add(Player player) {
        if (player.getType()
            .equalsIgnoreCase("football")) {
            return super.add(player);
        }
        throw new IllegalArgumentException("Not a football player");
    }
}

class TopFootballPlayers extends FootballPlayers {

    @Override
    protected boolean add(Player player) {
        if (player.getRank() < 10) {
            return super.add(player);
        }
        throw new IllegalArgumentException("Not a top player");
    }
}