package com.baeldung.compareto;

public class FootballPlayer implements Comparable<FootballPlayer> {

    private final String name;
    private final int goalsScored;

    public FootballPlayer(String name, int goalsScored) {
        this.name = name;
        this.goalsScored = goalsScored;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(FootballPlayer anotherPlayer) {
        return Integer.compare(this.goalsScored, anotherPlayer.goalsScored);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        FootballPlayer player = (FootballPlayer) object;
        return name.equals(player.name);
    }

}
